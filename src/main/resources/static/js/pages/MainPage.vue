<template>
    <v-app>
        <v-content class="bg">
            <v-container>
                <div class="clearfix mt-3">
                    <a href="#" class="float-right link">Поддержка</a>
                    <a href="#" class="float-right mr-7 link">Русский</a>
                </div>

                <v-card class="formCard mx-auto" elevation="12">
                    <v-card-title class="cardText pt-6 pl-7 headline font-weight-medium">Забронируйте жилье за несколько
                        минут
                    </v-card-title>
                    <v-form ref="form" v-model="valid">
                        <v-row class="px-7" justify="space-between">
                            <v-col cols="12" md="6" lg="3">
                                <p class="mb-1">Город</p>
                                <v-autocomplete
                                        v-model="city"
                                        :rules="cityRules"
                                        :loading="loading"
                                        :items="cities"
                                        :search-input.sync="search"
                                        cache-items
                                        flat
                                        hide-no-data
                                        hide-details
                                        outlined
                                        placeholder="Город пребывания"
                                        color="orange"
                                ></v-autocomplete>
                            </v-col>

                            <v-col cols="12" sm="6" md="3" lg="2">
                                <p class="mb-1">Прибытие</p>
                                <v-menu
                                        v-model="menu"
                                        :close-on-content-click="false"
                                        transition="slide-x-transition"
                                        min-width="290px"
                                >
                                    <template v-slot:activator="{ on }">
                                        <v-text-field
                                                v-model="computedCheckIn"
                                                :rules="dateRules"
                                                :value="computedCheckIn"
                                                placeholder="дд.мм.гггг"
                                                color="orange"
                                                readonly
                                                outlined
                                                v-on="on"
                                        ></v-text-field>
                                    </template>
                                    <v-date-picker color="orange" v-model="checkInDate"
                                                   :allowed-dates="getAllowedCheckInDates" locale="ru" no-title
                                                   scrollable
                                                   @input="menu = false"/>
                                </v-menu>
                            </v-col>

                            <v-col cols="12" sm="6" md="3" lg="2">
                                <p class="mb-1">Выезд</p>
                                <v-menu
                                        v-model="menu2"
                                        :close-on-content-click="false"
                                        transition="scale-transition"
                                        min-width="290px"
                                >
                                    <template v-slot:activator="{ on }">
                                        <v-text-field
                                                v-model="computedCheckOut"
                                                :rules="dateRules"
                                                :value="computedCheckOut"
                                                placeholder="дд.мм.гггг"
                                                color="orange"
                                                readonly
                                                outlined
                                                v-on="on"
                                        />
                                    </template>
                                    <v-date-picker color="orange" v-model="checkOutDate"
                                                   :allowed-dates="getAllowedCheckOutDates" locale="ru" no-title
                                                   scrollable
                                                   @input="menu2 = false"/>
                                </v-menu>
                            </v-col>

                            <v-col cols="12" md="6" lg="3">
                                <p class="mb-1">Гости</p>
                                <v-select
                                        v-model="guests"
                                        :rules="guestsRules"
                                        :items="guestsList"
                                        append-icon="expand_more"
                                        placeholder="Гости"
                                        outlined
                                        color="orange"
                                />
                            </v-col>

                            <v-col cols="12" md="3" lg="2">
                                <v-btn text tile block x-large class="mt-7 orange align-content-center white--text"
                                       @click="click">Искать
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-form>
                </v-card>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import cityApi from 'api/city';
    import apartmentsApi from 'api/apartments';

    let timeout;

    export default {
        name: "MainPage",
        data() {
            return {
                city: null,
                cityRules: [
                    v => !!v || 'Выберите город'
                ],
                cities: [],
                loading: false,
                search: null,
                checkInDate: '',
                checkOutDate: '',
                dateRules: [
                    v => !!v || 'Выберите дату'
                ],
                menu: false,
                menu2: false,
                guestsList: ['1 взрослый', '2 взрослых', '3 взрослых'],
                guests: '',
                guestsRules: [
                    v => !!v || 'Выберите количество гостей'
                ],
                valid: false,
            }
        },
        watch: {
            search(val) {
                val && val !== this.select && this.querySelections(val)
            }
        },
        computed: {
            computedCheckIn() {
                return this.checkInDate ? new Date(this.checkInDate).toLocaleString('ru', {
                    day: '2-digit',
                    month: 'long',
                    year: 'numeric'
                }) : '';
            },
            computedCheckOut() {
                return this.checkOutDate ? new Date(this.checkOutDate).toLocaleString('ru', {
                    day: '2-digit',
                    month: 'long',
                    year: 'numeric'
                }) : '';
            },
        },
        methods: {
            click() {
                if (this.$refs.form.validate()) {
                    const orderInfo = {
                        city: this.city.substr(0, this.city.indexOf(',')),
                        checkIn: this.checkInDate,
                        checkOut: this.checkOutDate,
                        guests: this.guests.split(' ')[0]
                    };
                    this.$router.push('/apartments?city=' + orderInfo.city + "&checkIn=" + orderInfo.checkIn + "&checkOut=" + orderInfo.checkOut + "&guests=" + orderInfo.guests);
                }

            },
            getAllowedCheckOutDates(val) {
                return val > this.checkInDate;
            },
            getAllowedCheckInDates(val) {
                const today = new Date();
                return (new Date(val) - new Date(today.toDateString())) >= 0;
            },
            querySelections(val) {
                this.loading = true;
                clearTimeout(timeout);
                timeout = setTimeout(() => {
                    cityApi.get(val).then(result => {
                        result.json().then(data => {
                            data.forEach(city => this.cities.push(city.name + ', ' + city.region.name));
                        });
                    });
                    this.loading = false;
                }, 500);
            },
        },
    }
</script>

<style scoped>
    .bg {
        background-image: url("https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1413&q=80");
        background-size: cover;
        background-position: bottom;
    }

    .clearfix::after {
        content: "";
        display: table;
        clear: both;
    }

    .link {
        text-decoration: none;
        color: #fff;
        height: 40px;
        box-sizing: border-box;
        text-shadow: 0 0 3px #000;
        font-size: 17px;
    }

    .link:hover {
        border-bottom: 2px solid #fff;
    }

    .formCard {
        width: 90%;
        margin-top: 240px;
    }

    @media (max-width: 1263px) {
        .formCard {
            margin-top: 100px;
        }
    }
</style>
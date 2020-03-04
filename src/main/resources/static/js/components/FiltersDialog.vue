<template>
    <v-dialog v-model="dialog" max-width="600px">
        <template v-slot:activator="{ on }">
            <v-btn class="ml-1 ml-md-6 hidden-sm-and-down" rounded outlined v-on="on">Фильтры</v-btn>
            <v-btn class="ml-1 ml-md-6 hidden-md-and-up hidden-xs-only" rounded outlined v-on="on" small>Фильтры</v-btn>
            <v-btn class="ml-1 ml-md-6 hidden-sm-and-up" rounded outlined v-on="on" x-small>Фильтры</v-btn>
        </template>
        <v-card>
            <v-icon class="icon" @click="dialog = false">close</v-icon>
            <v-card-title class="justify-center">
                <span class="headline">Фильтры</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-form ref="form" v-model="valid">
                        <v-row class="px-7" justify="space-between">
                            <v-col cols="12">
                                <p class="mb-1">Город</p>
                                <v-autocomplete
                                        v-model="city"
                                        :loading="loading"
                                        :items="cities"
                                        :search-input.sync="search"
                                        :rules="cityRules"
                                        cache-items
                                        flat
                                        hide-no-data
                                        hide-details
                                        outlined
                                        placeholder="Город пребывания"
                                        color="orange"
                                ></v-autocomplete>
                            </v-col>

                            <v-col cols="6">
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
                                                :value="computedCheckIn"
                                                :rules="dateRules"
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

                            <v-col cols="6">
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
                                                :value="computedCheckOut"
                                                :rules="dateRules"
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

                            <v-col cols="12">
                                <p class="mb-1 mt-n7">Гости</p>
                                <v-select
                                        v-model="guests"
                                        :items="guestsList"
                                        :rules="guestsRules"
                                        append-icon="expand_more"
                                        placeholder="Гости"
                                        outlined
                                        color="orange"
                                />
                            </v-col>
                        </v-row>
                    </v-form>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn tile @click="submit" class="orange white--text mb-1">Показать варианты</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import cityApi from 'api/city';

    let timeout;

    export default {
        name: "FiltersDialog",
        data() {
            return {
                city: '',
                checkInDate: '',
                checkOutDate: '',
                guests: '',
                dialog: false,
                cities: [],
                loading: false,
                search: null,
                menu: false,
                menu2: false,
                guestsList: ['1 взрослый', '2 взрослых', '3 взрослых'],
                cityRules: [
                    v => !!v || 'Выберите город'
                ],
                dateRules: [
                    v => !!v || 'Выберите дату'
                ],
                guestsRules: [
                    v => !!v || 'Выберите количество гостей'
                ],
                valid: false
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
            submit() {
                if (this.$refs.form.validate()) {
                    const orderInfo = {
                        city: this.city.substr(0, this.city.indexOf(',')),
                        checkIn: this.checkInDate,
                        checkOut: this.checkOutDate,
                        guests: this.guests.split(' ')[0]
                    };
                    this.$router.replace('/apartments?city=' + orderInfo.city + "&checkIn=" + orderInfo.checkIn + "&checkOut=" + orderInfo.checkOut + "&guests=" + orderInfo.guests);
                    this.$emit('updateApartments', {city: orderInfo.city, checkIn: orderInfo.checkIn, checkOut: orderInfo.checkOut, guests: orderInfo.guests});
                    this.dialog = false;
                }
            },
            getAllowedCheckOutDates(val) {
                return val > this.checkInDate;
            },
            getAllowedCheckInDates(val) {
                return (new Date(val).toLocaleDateString() >= new Date().toLocaleDateString());
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
    .icon {
        position: absolute;
        top: 15px;
        left: 10px;
    }
</style>
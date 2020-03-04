<template>
    <v-app>
        <v-app-bar app flat tile color="white" elevation="1">
            <div class="ml-md-6">
                <span class="text title">{{city}}</span>
                <span class="text subtitle-1 hidden-sm-and-down"> <b>·</b> {{computedCheckIn}} - {{computedCheckOut}} <b>·</b> </span>
                <span class="text subtitle-1 hidden-sm-and-down">Гостей: {{guests}}</span>
                <filters-dialog @updateApartments="updateApartments"/>
            </div>
            <div class="flex-grow-1"></div>
            <div>
                <a href="#" class="text mr-1 mr-md-7 link">Русский</a>
                <a href="#" class="text link">Поддержка</a>
            </div>
        </v-app-bar>
        <v-content class="bg">
            <v-container class="mt-5">
                <v-row>
                    <v-col v-for="apartment in apartments" :key="apartment.id" class="d-flex child-flex" cols="12"
                           sm="6" lg="4" xl="3">
                        <v-hover v-slot:default="{ hover }">
                            <v-card flat outlined :elevation="hover ? 12 : 0" @click="book(apartment.id)">
                                <div class="photoContainer">
                                    <v-img
                                            :src="apartment.photoUrl"
                                            class="photo"
                                            :class="{'on-hover': hover}"
                                            height="300px"
                                            aspect-ratio="1"
                                    >
                                    </v-img>
                                </div>

                                <div class="px-4 py-4">
                                    <h2 class="title">{{apartment.name}}</h2>
                                </div>

                                <v-card-text>
                                    <v-row
                                            align="center"
                                            class="ml-n1 mt-n4"
                                    >
                                        <v-rating
                                                :value="apartment.hotel.rating"
                                                color="amber"
                                                dense
                                                half-increments
                                                readonly
                                                size="14"
                                        ></v-rating>

                                        <div class="grey--text ml-4">{{apartment.hotel.rating}}</div>
                                    </v-row>

                                    <div class="subtitle-1 mt-2">
                                        <span class="font-weight-bold ">{{apartment.price}}₽</span> за ночь
                                    </div>

                                    <div class="subtitle-1">
                                        Ул. {{apartment.hotel.address.street}}, д. {{apartment.hotel.address.houseNumber}}
                                    </div>

                                    <div class="mt-3">
                                        <p class="text-justify text-truncate">{{apartment.description}}</p>
                                    </div>

                                    <div class="mt-2 font-weight-bold">
                                        Вместительность: {{apartment.capacity}} <br>
                                        Количество кроватей: {{apartment.bedNumber}}
                                    </div>
                                </v-card-text>
                            </v-card>
                        </v-hover>
                    </v-col>
                </v-row>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import FiltersDialog from 'components/FiltersDialog.vue';
    import apartmentsApi from 'api/apartments';

    export default {
        name: "HousesPage",
        components: {
            FiltersDialog
        },
        data() {
            return {
                city: '',
                checkInDate: '',
                checkOutDate: '',
                guests: '',
                apartments: [],
            }
        },
        methods: {
            book(id) {
                this.$router.push('/book?id=' + id + '&city=' + this.city + "&checkIn=" + this.checkInDate + "&checkOut=" + this.checkOutDate + "&guests=" + this.guests);
            },
            updateApartments(info) {
                this.apartments = [];
                this.city = info.city;
                this.checkInDate = info.checkIn;
                this.checkOutDate = info.checkOut;
                this.guests = info.guests;

                apartmentsApi.get(info.city, info.checkIn, info.checkOut, info.guests).then(result => {
                    result.json().then(data => {
                        data.forEach(apartment => this.apartments.push(apartment));
                    })
                });
            }
        },
        computed: {
            computedCheckIn() {
                return this.checkInDate ? new Date(this.checkInDate).toLocaleString('ru', {
                    day: '2-digit',
                    month: 'long',
                }) : '';
            },
            computedCheckOut() {
                return this.checkOutDate ? new Date(this.checkOutDate).toLocaleString('ru', {
                    day: '2-digit',
                    month: 'long',
                }) : '';
            }
        },
        mounted() {
            this.city = this.$route.query.city;
            this.checkInDate = this.$route.query.checkIn;
            this.checkOutDate = this.$route.query.checkOut;
            this.guests = this.$route.query.guests;
            apartmentsApi.get(this.city, this.checkInDate, this.checkOutDate, this.guests).then(result => {
                result.json().then(data => {
                    data.forEach(apartment => this.apartments.push(apartment));
                })
            });
        }
    }
</script>

<style scoped>
    .bg {
        background-color: #ffffff;
    }

    .link {
        display: inline-block;
        color: #414141;
        font-size: 17px;
        text-decoration: none;
        line-height: 63px;
        height: 63px;
        box-sizing: border-box;
    }

    .link:hover {
        border-bottom: 2px solid #414141;
    }

    .photoContainer {
        height: 300px;
        overflow: hidden;
    }

    .photo {
        transition: transform .3s;
    }

    .on-hover {
        transform: scale(1.2);
    }

    @media (max-width: 959px) {
        .text {
            font-size: 14px;
        }
    }

    @media (max-width: 850px) {
        .text {
            font-size: 12px;
        }
    }
</style>
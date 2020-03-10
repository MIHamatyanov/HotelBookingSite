<template>
    <v-app>
        <v-app-bar app flat tile color="white" elevation="1">
            <div class="ml-md-6">
                <span class="text title">{{city}}</span>
                <span class="text subtitle-1 hidden-sm-and-down"> <b>·</b> {{formattedDate(checkInDate)}} - {{formattedDate(checkOutDate)}} <b>·</b> </span>
                <span class="text subtitle-1 hidden-sm-and-down">Гости: {{guests}}</span>
            </div>
            <div class="flex-grow-1"></div>
            <div>
                <a href="#" class="text mr-1 mr-md-7 link">Русский</a>
                <a href="#" class="text link">Поддержка</a>
            </div>
        </v-app-bar>
        <v-content class="bg">
            <v-container class="justify-center">
                <div class="d-table mx-auto mt-5">
                    <h2>{{apartment.hotel.name}}</h2>
                    <div>
                        <v-rating
                                class="d-inline"
                                :value="apartment.hotel.rating"
                                color="amber"
                                dense
                                half-increments
                                readonly
                                size="14"
                        ></v-rating>
                        <b>·</b>
                        <span class="body-2">Ул. {{apartment.hotel.address.street}}, д. {{apartment.hotel.address.houseNumber}}</span>
                    </div>
                    <v-img class="mt-5 photo" :src="apartment.photoUrl" :alt="apartment.name"></v-img>
                    <v-window v-model="step" class="cont">
                        <v-window-item :value="1">
                            <v-row class="mt-7 mx-0" justify="space-between">
                                <v-col cols="12" md="4">
                                    <span class="title descriptionText">{{apartment.name}}</span>
                                    <br>
                                    <span class="subtitle-2 descriptionText">Вместительность: {{apartment.capacity}} <b>·</b> Количество кроватей: {{apartment.bedNumber}}</span>
                                    <br>
                                    <span class="mt-3 descriptionText">{{apartment.description}}</span>
                                </v-col>
                                <v-col class="mr-lg-3 button mt-sm-5 mt-md-0" cols="12" md="3">
                                    <div class="subtitle-1">
                                        <span class="font-weight-bold">{{apartment.price}}₽</span> за ночь <br>
                                        <span>{{formattedDate(checkInDate)}} - {{formattedDate(checkOutDate)}} <br> Количество гостей: {{guests}}</span>
                                        <br>
                                        <div class="options">
                                            <v-divider class="mt-3 mb-2"></v-divider>
                                            <span class="font-weight-bold">Итого: {{totalPrice}}₽</span>
                                            <v-divider class="mt-2"></v-divider>
                                        </div>
                                    </div>
                                    <v-btn x-large color="orange"
                                           class="white--text hidden-sm-and-down mt-5 btn"
                                           @click="nextStep">
                                        Забронировать
                                    </v-btn>
                                    <v-btn large color="orange" class="white--text hidden-md-and-up mt-5 btn"
                                           @click="nextStep">
                                        Забронировать
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-window-item>
                        <v-window-item :value="2">
                            <v-row class="mt-7 mx-0" justify="space-between">
                                <span class="title descriptionText">Введите свои данные</span>
                            </v-row>
                            <user-information-form @nextPage="book"/>
                        </v-window-item>
                    </v-window>
                </div>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import UserInformationForm from 'components/UserInformationForm.vue';
    import apartmentsApi from 'api/apartments';
    import ordersApi from 'api/orders';

    export default {
        name: "BookPage",
        components: {
            UserInformationForm
        },
        data() {
            return {
                city: '',
                checkInDate: '',
                checkOutDate: '',
                guests: '',
                apartment: {
                    hotel: {
                        address: {}
                    }
                },
                step: 1,
            }
        },
        computed: {
            totalPrice() {
                const date1 = new Date(this.checkInDate);
                const date2 = new Date(this.checkOutDate);
                const days = Math.ceil(Math.abs(date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
                return days * this.apartment.price;
            }
        },
        methods: {
            nextStep() {
                this.step = 2;
            },
            book(user) {
                const order = {
                    guest: user,
                    checkInDate: this.checkInDate,
                    checkOutDate: this.checkOutDate,
                    apartment: this.apartment
                };

                ordersApi.createOrder(order).then(result => {
                    result.json().then(data => {
                        this.$router.push('/confirm?order=' + data.id);
                    });
                });
            },
            formattedDate(date) {
                return date ? new Date(date).toLocaleString('ru', {
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

            const id = this.$route.query.id;
            apartmentsApi.getById(id).then(result => {
                result.json().then(data => {
                    this.apartment = data;
                })
            });
        }
    }
</script>

<style scoped>

    .descriptionText {
        display: inline-block;
        width: 600px;
        word-wrap: break-spaces;
    }

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

    .photo {
        width: 1200px;
        height: 500px;
    }

    .btn {
        width: 300px;
    }

    .options {
        width: 300px;
    }

    @media (max-width: 1263px) {
        .photo {
            width: 900px;
            height: 400px;
        }

        .button {
            margin-right: 60px;
        }

        .btn {
            width: 270px;
        }

        .options {
            width: 270px;
        }
    }


    @media (max-width: 959px) {
        .text {
            font-size: 14px;
        }

        .photo {
            width: 700px
        }
    }

    @media (max-width: 850px) {
        .text {
            font-size: 12px;
        }
    }

    @media (max-width: 720px) {
        .photo {
            width: 500px;
            height: 300px;
        }

        .descriptionText {
            width: 500px;
        }
    }

    @media (max-width: 599px) {
        .photo {
            margin-right: 10px;
            width: 100vw;
        }

        .cont {
            margin-right: 10px;
            width: 100vw;
        }
    }
</style>
<template>
    <v-app>
        <v-app-bar app flat tile color="white" elevation="1">
            <div class="ml-md-6">
                <span class="text title">{{city}}</span>
                <span class="text subtitle-1 hidden-sm-and-down"> <b>·</b> {{computedCheckIn}} - {{computedCheckOut}} <b>·</b> </span>
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
                <v-window v-model="page" class="cont">
                    <v-window-item :value="1">
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
                            <v-img class="mt-5 photo" :src="apartment.photoUrl"></v-img>
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
                                                <span>{{computedCheckIn}} - {{computedCheckOut}} <br> Количество гостей: {{guests}}</span> <br>
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
                    </v-window-item>
                    <v-window-item :value="2">
                        <v-card class=" d-table mx-auto mt-5" elevation="7">
                            <v-row justify="center" class="mt-5">
                                <h2>Бронирование подтверждено</h2>
                            </v-row>
                            <v-row justify="center" class="mx-12">
                                <table class="mt-5 table">
                                    <tbody>
                                    <tr>
                                        <td class="title">Время бронирования:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.creationDate}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Отель:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.apartment.hotel.name}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Адрес:</td>
                                        <td class="subtitle-1 ml-5">Ул. {{orderInfo.apartment.hotel.address.street}}, д. {{orderInfo.apartment.hotel.address.houseNumber}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="title">Дата заезда:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.checkInDate}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Дата выезда:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.checkOutDate}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Имя:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.guest.name}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Номер для связи:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.guest.phoneNumber}}</td>
                                    </tr>
                                    <tr>
                                        <td class="title">Email:</td>
                                        <td class="subtitle-1 ml-5">{{orderInfo.guest.email}}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </v-row>
                            <v-row justify="center mt-10">
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                     version="1.1" id="Capa_1" x="0px" y="0px" viewBox="0 0 512 512"
                                     style="enable-background:new 0 0 512 512;" xml:space="preserve" width="130px"
                                     height="130px" class=""><g><g><g><path d="M497.36,69.995c-7.532-7.545-19.753-7.558-27.285-0.032L238.582,300.845l-83.522-90.713    c-7.217-7.834-19.419-8.342-27.266-1.126c-7.841,7.217-8.343,19.425-1.126,27.266l97.126,105.481    c3.557,3.866,8.535,6.111,13.784,6.22c0.141,0.006,0.277,0.006,0.412,0.006c5.101,0,10.008-2.026,13.623-5.628L497.322,97.286    C504.873,89.761,504.886,77.54,497.36,69.995z" class="active-path" fill="#00AF38"/></g></g><g><g><path d="M492.703,236.703c-10.658,0-19.296,8.638-19.296,19.297c0,119.883-97.524,217.407-217.407,217.407    c-119.876,0-217.407-97.524-217.407-217.407c0-119.876,97.531-217.407,217.407-217.407c10.658,0,19.297-8.638,19.297-19.296    C275.297,8.638,266.658,0,256,0C114.84,0,0,114.84,0,256c0,141.154,114.84,256,256,256c141.154,0,256-114.846,256-256    C512,245.342,503.362,236.703,492.703,236.703z" class="active-path" fill="#00AF38"/></g></g></g> </svg>
                            </v-row>
                            <v-row justify="center mt-10 mb-5">
                                <v-btn x-large color="orange"
                                       class="white--text"
                                       href="/">
                                    На главную
                                </v-btn>
                            </v-row>
                        </v-card>
                    </v-window-item>
                </v-window>
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
                page: 1,
                orderInfo: {
                    guest: {},
                    apartment: {
                        hotel: {
                            address: {}
                        }
                    }
                }
            }
        },
        computed: {
            totalPrice() {
                const date1 = new Date(this.checkInDate);
                const date2 = new Date(this.checkOutDate);
                const days = Math.ceil(Math.abs(date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
                return days * this.apartment.price;
            },
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
                    result.json().then(data => this.orderInfo = data);
                });

                this.page = 2;
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

    .table {
        width: 500px;
    }

    .table tr td:last-child {
        text-align: right;
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
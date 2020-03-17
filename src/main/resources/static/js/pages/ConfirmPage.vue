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
                <v-card class=" d-table mx-auto mt-5" elevation="7">
                    <v-row justify="center" class="mt-5">
                        <h2>Бронирование подтверждено</h2>
                    </v-row>
                    <v-row justify="center" class="mx-12">
                        <span class="title">Информация о бронировании отправлена на указаный почтовый ящик.</span>
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
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import ordersApi from 'api/orders';

    export default {
        name: "ConfirmPage",
        data() {
            return {
                city: '',
                checkInDate: '',
                checkOutDate: '',
                guests: '',
            }
        },
        methods: {
            formattedDate(date) {
                return date ? new Date(date).toLocaleString('ru', {
                    day: '2-digit',
                    month: 'long',
                }) : '';
            }
        },
        mounted() {
            ordersApi.getById(this.$route.query.order).then(result => {
                result.json().then(data => {
                    this.checkInDate = data.checkInDate;
                    this.checkOutDate = data.checkOutDate;
                    this.city = data.apartment.hotel.address.city.name;
                    this.guests = data.apartment.capacity;
                });
            })
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

    .table tr td:last-child {
        text-align: right;
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
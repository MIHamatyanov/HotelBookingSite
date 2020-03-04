<template>
    <v-form ref="form" v-model="valid" class="mt-5">
        <v-row>
            <v-col
                    cols="12"
                    sm="5"
                    md="4"
            >
                <p class="mb-1">Имя</p>
                <v-text-field
                        v-model="username"
                        :rules="nameRules"
                        placeholder="Фамилия и имя"
                        outlined
                        required
                ></v-text-field>
            </v-col>

            <v-col
                    cols="12"
                    sm="5"
                    md="4"
            >
                <p class="mb-1">Номер телефона</p>
                <v-text-field
                        v-model="phoneNumber"
                        :rules="phoneRules"
                        placeholder="88005553535"
                        outlined
                        required
                ></v-text-field>
            </v-col>

            <v-col
                    cols="12"
                    sm="5"
                    md="4"
            >
                <p class="mb-1">Email</p>
                <v-text-field
                        v-model="email"
                        :rules="emailRules"
                        placeholder="example@mail.ru"
                        outlined
                        required
                ></v-text-field>
            </v-col>
        </v-row>

        <v-btn x-large color="orange" class="white--text btn" @click="submit">
            Подтвердить
        </v-btn>
    </v-form>
</template>

<script>
    export default {
        name: "UserInformationForm",
        data: () => ({
            valid: false,
            username: '',
            nameRules: [
                v => !!v || 'Введите имя'
            ],
            phoneNumber: '',
            phoneRules: [
                v => !!v || 'Введите номер телефона',
                v => /^\d{11}$/.test(v) || 'Неверные данные'
            ],
            email: '',
            emailRules: [
                v => !!v || 'Введите email',
                v => /.+@.+/.test(v) || 'Некорректный email',
            ],
        }),
        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    this.$emit('nextPage', {name: this.username, phoneNumber: this.phoneNumber, email: this.email});
                }
            }
        }
    }
</script>

<style scoped>
    .btn {
        width: 385px;
        float: right;
    }

    @media (max-width: 1263px) {
        .btn {
            width: 285px;
        }
    }

    @media (max-width: 959px) {
        .btn {
            width: 280px;
            float: none;
        }
    }

</style>
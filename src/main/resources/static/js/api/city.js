import Vue from 'vue'

const city = Vue.resource('/api/city');

export default {
    get: cityStartsWith => city.get({cityStartsWith}),
}
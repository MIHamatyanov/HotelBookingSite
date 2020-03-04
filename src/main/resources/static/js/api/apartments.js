import Vue from 'vue'

const apartments = Vue.resource('/api/apartments/{id}');

export default {
    get: (city, checkIn, checkOut, guests) => apartments.get({city, checkIn, checkOut, guests}),
    getById: (id) => apartments.get({id})
}
import Vue from 'vue'

const apartments = Vue.resource('/api/orders/{id}');

export default {
    createOrder: (order) => apartments.save({}, order),
}
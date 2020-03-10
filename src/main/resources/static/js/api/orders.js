import Vue from 'vue'

const orders = Vue.resource('/api/orders/{id}');

export default {
    getById: (id) => orders.get({id}),
    createOrder: (order) => orders.save({}, order),
}
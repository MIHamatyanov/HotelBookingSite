import Vue from 'vue';
import VueRouter from 'vue-router';
import MainPage from 'pages/MainPage.vue';
import HousesPage from 'pages/HousesPage.vue';
import BookPage from 'pages/BookPage.vue';
import ConfirmPage from 'pages/ConfirmPage.vue';

Vue.use(VueRouter);

const routes = [
    {path: '/', component: MainPage },
    {path: '/apartments', component: HousesPage},
    {path: '/book', component: BookPage},
    {path: '/confirm', component: ConfirmPage},
    {path: '*', component: MainPage}
];

export default new VueRouter({
    mode: 'history',
    routes
})
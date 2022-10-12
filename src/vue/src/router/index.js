import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login";
import NotFound from "../views/NotFound";
import axios from "axios";
import store from "../store/index.js";
import multiguard from "vue-router-multiguard";

Vue.use(VueRouter);

// 権限チェック
const authGuard = (to, from, next) => {
    axios
        .get("/api/islogin", {
            headers: {
                "X-AUTH-TOKEN": "Bearer " + store.getters.getToken,
            },
        })
        .then((res) => {
            console.log(res);
            next();
        })
        .catch(() => {
            alert("ログインしてください");
            next("/login");
        });
};

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
        beforeEnter: multiguard([authGuard]),
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    },

    // 必ず最後にする
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;

import Vue from "vue";
import VueRouter from "vue-router";

import Home from "@/views/Home.vue";
import Login from "@/views/Login";
import NotFound from "@/views/NotFound";

import axios from "axios";
import store from "@/store/index.js";
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
            store.dispatch("saveToken", res.headers["x-auth-token"]);
            next();
        })
        .catch(() => {
            alert("ログインしてください");

            // 保存されているデータの削除
            store.dispatch("removeToken");
            next("/login");
        });
};

// ログイン画面に遷移する時、ログインしている場合はHomeに戻す
const loginGuard = (to, from, next) => {
    axios
        .get("/api/islogin", {
            headers: {
                "X-AUTH-TOKEN": "Bearer " + store.getters.getToken,
            },
        })
        .then(() => {
            next("/inv_g0006");
        })
        .catch(() => {
            // 保存されているデータの削除
            store.dispatch("removeToken");
            next();
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
        beforeEnter: multiguard([loginGuard]),
    },

    // 必ず最後にする
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
        beforeEnter: multiguard([authGuard]),
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;

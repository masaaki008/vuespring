import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    state() {
        return {
            token: "",
        };
    },

    getters: {
        getToken(state) {
            return state.token;
        },
    },

    mutations: {
        saveToken(state, token) {
            state.token = token;
        },
        removeToken(state) {
            state.token = "";
        },
    },

    actions: {
        saveToken({ commit }, token) {
            commit("saveToken", token);
        },
        removeToken({ commit }) {
            commit("removeToken");
        },
    },

    modules: {},

    plugins: [
        createPersistedState({
            key: "inv",
        }),
    ],
});

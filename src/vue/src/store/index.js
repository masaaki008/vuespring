import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import SecureLS from "secure-ls";
const ls = new SecureLS({ isCompression: false });

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
            storage: {
                getItem: (key) => ls.get(key),
                setItem: (key, value) => ls.set(key, value),
                removeItem: (key) => ls.remove(key),
            },
        }),
    ],
});

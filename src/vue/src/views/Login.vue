<template>
    <div>
        <b-row>
            <h1>ログイン</h1>
        </b-row>

        <b-form>
            <b-row class="justify-content-center">
                <b-col cols="12" xl="6">
                    <b-form-input
                        id="mailAddress"
                        v-model="form.mailAddress"
                        type="email"
                        placeholder="メールアドレス"
                    ></b-form-input>
                </b-col>
            </b-row>

            <b-row class="justify-content-center">
                <b-col cols="12" xl="6">
                    <b-form-input
                        id="password"
                        v-model="form.password"
                        type="password"
                        placeholder="パスワード"
                    ></b-form-input>
                </b-col>
            </b-row>
        </b-form>

        <b-row class="justify-content-center">
            <b-col cols="3">
                <b-button class="mt-1 w-100" variant="primary" @click="login"
                    >ログイン</b-button
                >
            </b-col>
            <b-col cols="3">
                <b-button class="mt-1 w-100" variant="primary" @click="test"
                    >サンプル</b-button
                >
            </b-col>
            <b-col cols="3">
                <b-button class="mt-1 w-100" variant="primary" @click="logout"
                    >ログアウト</b-button
                >
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "Login",
    data() {
        return {
            form: {
                mailAddress: "",
                password: "",
            },
        };
    },
    methods: {
        login() {
            axios
                .post("/api/login", {
                    mailAddress: this.form.mailAddress,
                    password: this.form.password,
                })
                .then((res) => {
                    console.log(res);
                    this.$store.dispatch(
                        "saveToken",
                        res.headers["x-auth-token"]
                    );
                    this.$router.push("/");
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        test() {
            axios
                .get("/api/sample", {
                    headers: {
                        "X-AUTH-TOKEN":
                            "Bearer " + this.$store.getters.getToken,
                    },
                })
                .then((res) => {
                    console.log(res);
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        logout() {
            axios
                .post("/api/logout")
                .then((res) => {
                    console.log(res);
                    this.$store.dispatch("removeToken");
                })
                .catch((e) => {
                    console.log(e);
                });
        },
    },
};
</script>

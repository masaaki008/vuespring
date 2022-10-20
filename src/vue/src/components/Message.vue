<template>
    <transition name="message">
        <div v-if="messageActive" id="message-parent">
            <div id="message">
                <b-alert
                    class="u-pre-wrap"
                    v-text="message"
                    :show="dismissCountDown"
                    v-bind:variant="variant"
                    @dismiss-count-down="countDownChanged"
                ></b-alert>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    name: "Message",

    data() {
        return {
            // message表示用データ
            dismissSecs: 3,
            dismissCountDown: 0,
            message: "",
            variant: "",
            messageActive: false,
        };
    },

    methods: {
        countDownChanged(dismissCountDown) {
            this.dismissCountDown = dismissCountDown;
            if (dismissCountDown == 0) {
                this.messageActive = false;
            }
        },
        showMessage(message, variant) {
            this.message = message;
            this.variant = variant;
            this.dismissCountDown = this.dismissSecs;
            this.messageActive = true;
        },
    },
};
</script>

<style scoped>
.message-enter-active,
.message-leave-active {
    transition: opacity 0.5s;
}

.message-enter,
.message-leave-to {
    opacity: 0;
}

#message-parent {
    width: 100%;
}

#message {
    position: absolute;
    top: 5px;
    right: 0;
    left: 0;
    margin-left: auto;
    margin-right: auto;
    box-sizing: border-box;
    width: 70%;
}
</style>

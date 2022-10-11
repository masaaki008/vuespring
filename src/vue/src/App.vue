<template>
    <div id="app">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item to="/">Home</b-nav-item>
                    <b-nav-item to="/login">Login</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>

        <b-container fluid>
            <router-view />
        </b-container>
    </div>
</template>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}

/*nav {*/
/*    padding: 30px;*/
/*}*/

/*nav a {*/
/*    font-weight: bold;*/
/*    color: #2c3e50;*/
/*}*/

/*nav a.router-link-exact-active {*/
/*    color: #42b983;*/
/*}*/
</style>

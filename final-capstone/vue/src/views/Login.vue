<template>
  <div id="login" class="text-center">
    <div class="head-wrapper">
      <div class="logo">
        <img
          src="../Images/robot-image.png"
          alt="robot-image"
          width="90px"
          height="95px"
        />
        <h2>HALPER</h2>
      </div>
    </div>
    <h1 class="h3 mb-3 font-weight-normal">Login</h1>
    <form class="form-signin" @submit.prevent="login">
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >
        Thank you for registering, please sign in.
      </div>

      <label for="username" id="first-input" class="sr-only">Username:</label>

      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <br />
      <label for="password" class="sr-only">Password: </label>

      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <br />
      <button type="submit">Sign in</button>

      <router-link :to="{ name: 'register' }">Need an account?</router-link>
    </form>
  </div>
</template>

<script>
import AuthService from "../services/AuthService";
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);

            AuthService.getUserDetails(this.user.username).then((response) => {
              const user_details = response.data;
              this.$store.commit("SET_USER_DETAILS", user_details);
              this.$router.push("/home");
            });
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
<style scoped>
.logo {
  display: flex;
  flex-direction: row;
  width: 100%;
  margin-top: -75px;
  margin-left: -80px;
}

.logo h2 {
  font-family: "Poppins", sans-serif;
  font-size: 40px;
  color: #343a40;
  margin-top: 20px;
  margin-left: 3%;
}

form {
  margin-left: 3.5%;
  margin-top: 3%;
  background-color: rgba(201, 76, 76, 0.3);
  padding-left: 30px;
  margin-right: 69.3%;
  padding-top: 30px;
  padding-bottom: 30px;
}
h1 {
  font-family: "Poppins", sans-serif;
  font-size: 60px;
  letter-spacing: 3px;
  color: #343a40;
  margin: 0;
  margin-left: 50px;
}

label {
  font-size: 22px;
  color: #caf0f8;
  font-family: "Oswald", sans-serif;
  margin-right: 20px;
}

input {
  margin-left: 0.5%;
  margin-top: 2%;
}

#first-input {
  margin-top: 0%;
}

a {
  margin-left: 35px;
  font-size: 18px;
  font-family: "Oswald", sans-serif;
  color: #caf0f8;
  background-color: #0e3a8d;
  transition: ease-in-out 0.8s;
  padding: 8px;
  padding-left: 15px;
  padding-right: 15px;
  padding-top: 5px;
  text-decoration: none;
  border-radius: 4px;
}

a:hover {
  color: #0e3a8d;
  background-color: #0076b6;
  text-decoration: underline;
}
button {
  font-family: "Poppins", sans-serif;
  font-size: 18px;
  margin-top: 30px;
}

.alert {
  font-size: 40px;
  color: #caf0f8;
  font-family: "Oswald", sans-serif;
}
.text-center {
  margin-left: 3%;
  margin-top: 5%;
}
</style>

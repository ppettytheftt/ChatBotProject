<template>
  <div>
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
      <div id="nav" class="nav-wrapper">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
      </div>
    </div>
    <h1>My Account</h1>
    <div class="accountdetails">
      <label class="un" for="username" id="first-username">Username:</label>
      <h3 id="username" class="un">{{ this.$store.state.user.username }}</h3>

      <label for="firstname" class="fn">Firstname:</label>
      <h3 id="firstname" class="fn">
        {{ this.$store.state.user_details.firstName }}
      </h3>

      <label for="lastname" class="ln">Lastname:</label>
      <h3 id="lastname" class="ln">
        {{ this.$store.state.user_details.lastName }}
      </h3>
      <label for="emailId" class="email">Email Id:</label>
      <h3 id="emailId" class="email">
        {{ this.$store.state.user_details.emailId }}
      </h3>

      <label for="zip" class="zip">Zip Code:</label>
      <h3 id="zip" class="zip">
        {{ this.$store.state.user_details.zip }}
      </h3>
      <label for="contactnumber" class="contact">Contact Number:</label>
      <h3 id="contactnumber" class="contact">
        {{ this.$store.state.user_details.contactNumber }}
      </h3>
      <a
        class="show-form"
        href="#"
        v-on:click="showForm = true"
        v-if="showForm === false"
        >Show Update Form</a
      >
    </div>

    <div class="update-form-wrapper" v-if="showForm === true">
      <form class="update" v-on:submit.prevent="">
        <label for="username" id="first-username">Username:</label>
        <input type="text" class="input-user" v-model="user.userName" />
        <br />
        <label for="firstname" id="form-firstname">Firstname:</label>
        <input
          type="text"
          class="input-firstname"
          v-model="user_details.firstName"
        />
        <br />
        <label for="lastname" id="form-lastname">Lastame:</label>

        <input
          type="text"
          class="input-lastname"
          v-model="user_details.lastName"
        />
        <br />
        <label for="emailId" id="form-email">Email Id:</label>
        <input type="text" class="input-email" v-model="user_details.emailId" />
        <br />
        <label for="zip" id="form-zip">Zip Code:</label>
        <input type="text" class="input-zip" v-model="user_details.zip" />
        <br />
        <label for="contactnumber" id="form-number">Contact Number:</label>
        <input
          type="text"
          class="input-number"
          v-model="user_details.contactNumber"
        />
        <br />
        <label for="checkbox" id="form-student"
          >Student (leave unchecked if alumni)</label
        >
        <input
          type="checkbox"
          class="input-student"
          v-model="user_details.isStudent"
        />

        <button v-on:click.prevent="updateDetails">Update</button>
      </form>
    </div>
  </div>
</template>

<script>
import AuthService from "../services/AuthService.js";
export default {
  name: "my-account",

  data() {
    return {
      showForm: false,
      user_details: {
        firstName: "",
        lastName: "",
        emailId: "",
        zipCode: "",
        contactNumber: "",
        isStudent: "",
        zip: "",
      },

      user: {
        userName: "",
      },
    };
  },
  methods: {
    updateDetails() {
      this.showForm = false;
      AuthService.updateUserAccountDetails(
        this.user.userName,
        this.user_details
      ).then((response) => {
        if (response.status == 200) {
          const user_details = response.data;
          this.$store.commit("SET_USER_DETAILS", user_details);
          //this.$router.push("/home");
        }
      });
    },
  },
};
</script>

<style>
.head-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
}

.nav-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 70%;
}

.logo {
  display: flex;
  flex-direction: row;
  width: 30%;
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
}

h1 {
  color: #221717;
}
label {
  font-size: 22px;
  color: #221717;
  font-family: "Oswald", sans-serif;
}
.un,
.fn,
.ln,
.email,
.contact,
.zip {
  text-transform: capitalize;
  font-size: 22px;
  color: #221717;
  font-family: "Oswald", sans-serif;
  padding: 10px;
  margin: 0;
  width: 200px;
}

.update input {
  background-color: #f8f9fa;
  width: 200px;
  height: 20px;
  padding-left: 5px;
}

.input-firstname,
.input-lastname,
.input-email,
.input-number,
.input-zip {
  float: right;
  margin-top: 21px;
  margin-right: 80px;
}
.input-user {
  float: right;
  margin-top: 5px;
  margin-right: 80px;
}

#form-firstname,
#form-lastname,
#form-email,
#form-zip,
#form-number,
#form-student {
  display: inline-block;
  margin-top: 15px;
}

.input-student {
  margin-left: -75px;
  margin-top: 5px;
}

.accountdetails {
  display: grid;
  grid-template-columns: 1fr 8fr;
  grid-template-areas:
    "ga-username ga-username-value "
    "ga-firstname ga-firstname-value"
    "ga-lastname ga-lastname-value"
    "ga-email ga-email-value"
    "ga-contact ga-contact-value"
    "ga-zip ga-zip-value";
  padding: 20px;
  margin-top: 8px;
  margin-left: 30px;
  margin-right: 60%;
  background-color: rgba(201, 76, 76, 0.3);
  width: 500px;
}

.update-form-wrapper {
  padding-bottom: 15px;
  margin-right: 60%;
  margin-left: 35px;
  background-color: rgba(201, 76, 76, 0.3);
  width: 535px;
}

#first-username {
  grid-area: ga-username;
}
#username {
  grid-area: ga-username-value;
}
#zip {
  grid-area: ga-zip-value;
}

.fn {
  grid-area: ga-firstname;
}
#firstname {
  grid-area: ga-firstname-value;
}

.ln {
  grid-area: ga-lastname;
}

#lastname {
  grid-area: ga-lastname-value;
}

#emailId {
  grid-area: ga-email-value;
}
.email {
  grid-area: ga-email;
}
#contactnumber {
  grid-area: ga-contact-value;
  letter-spacing: 1.2px;
}
.contact {
  grid-area: ga-contact;
}
.input-zip {
  grid-area: ga-zip;
}
#nav > a {
  font-family: "Poppins", sans-serif;
  font-size: 25px;
  letter-spacing: 3px;
  text-decoration: none;
  color: #caf0f8;
  margin-top: 25px;
  margin-bottom: 20px;
  margin-left: 3%;
  margin-right: 3%;
  text-align: center;
  padding: 3px 60px 0px 60px;
  background-image: linear-gradient(to bottom left, #168aad, #1a759f, #1e6091);
  border-radius: 8px;
  transition: ease-out 0.8s;
}

#nav > a:hover {
  text-decoration: underline;
  font-size: 37px;
  color: #343a40;
  background-image: linear-gradient(to bottom left, #34a0a4, #52b69a);
}
#nav {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 70%;
}

.show-form {
  padding-top: 10px;
  padding-bottom: 10px;
  text-align: center;
  background-image: linear-gradient(to bottom left, #168aad, #1a759f, #1e6091);
  font-family: "Poppins", sans-serif;
  font-size: 20px;
  text-decoration: none;
  color: #caf0f8;
  text-decoration: none;
  transition: ease-out 0.8s;
}

.show-form:hover {
  background-image: linear-gradient(to bottom left, #34a0a4, #52b69a);
  color: #343a40;
}

button {
  text-align: center;
  background-image: linear-gradient(to bottom left, #168aad, #1a759f, #1e6091);
  font-family: "Poppins", sans-serif;
  font-size: 16px;
  text-decoration: none;
  color: #caf0f8;
  text-decoration: none;
  transition: ease-out 0.8s;
  width: 150px;
  height: 40px;
  margin-top: 10px;
}

button:hover {
  color: #343a40;
  background-image: linear-gradient(to bottom left, #34a0a4, #52b69a);
}
</style>
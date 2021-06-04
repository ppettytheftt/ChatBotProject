<template>
  <div id="register" class="text-center">
    <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    <form class="form-register" @submit.prevent="register">
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username:</label>
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

      <label for="first-name">First Name:</label>
      <input
        type="text"
        id="firstName"
        class="form-control"
        v-model="user.firstName"
        required
      />
      <br />

      <label for="last-name">Last Name:</label>
      <input
        type="text"
        id="lastName"
        class="form-control"
        v-model="user.lastName"
        required
      />
      <br />

      <label for="emailId">Email:</label>
      <input
        type="text"
        id="email"
        class="form-control"
        v-model="user.emailId"
        required
      />
      <br />
      <label for="zip">Zip Code:</label>
      <input
        type="text"
        id="zip"
        class="form-control"
        v-model="user.zip"
        required
      />
      <br />

      <label for="contact-number">Phone Number:</label>
      <input
        type="number"
        id="contact-number"
        class="form-control"
        v-model="user.contactNumber"
        required
      />

      <!-- TODO need to find how the radio ties into our information to relay that to the database -->
      <br />
      <label for="isStudent">Student: </label>
      <input type="checkbox" id="isStudent" v-model="user.isStudent" />
      <br />

      <label for="password" class="sr-only">Password:</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <br />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <br />
      <router-link id="haveAccountButton" :to="{ name: 'login' }"
        >Have an account?</router-link
      >
      <button
        class="btn btn-lg btn-primary btn-block"
        id="submit"
        type="submit"
      >
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
        firstName: "",
        lastName: "",
        emailId: "",
        zip: "",
        contactNumber: 0,
        isStudent: true,
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
form {
  margin-left: 3.5%;
}

input {
  background-color: #f8f9fa;
  width: 200px;
  height: 20px;
  padding-left: 5px;
}

#username {
  margin-left: 40px;
}

#firstName {
  margin-left: 34px;
  margin-top: -1px;
}

#lastName {
  margin-left: 37px;
  margin-top: -1px;
}

#email {
  margin-left: 80px;
  margin-top: -1px;
}

#contact-number {
  margin-left: 3px;
  margin-top: -1px;
}

#zip {
  margin-left: 54px;
  margin-top: -1px;
}

#isStudent {
  margin-left: -35px;
  margin-top: 3px;
}

#password {
  margin-left: 45px;
  margin-top: -1px;
}

#confirmPassword {
  margin-left: 130px;
  margin-top: 7px;
}

#haveAccountButton {
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

#submit {
  margin-left: 37px;
  border-radius: 4px;
}

#haveAccountButton:hover {
  background-color: #0076b6;
}

form {
  background-color: rgba(201, 76, 76, 0.3);
  margin-left: 30px;
  margin-right: 1100px;
  padding-left: 15px;
  padding-bottom: 15px;
}
</style>
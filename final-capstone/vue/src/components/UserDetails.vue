<template>
  <div>
    {{ user.firstName }}
  </div>
</template>

<script>
import AuthService from "../services/AuthService.js";
export default {
  name: "userdetails",
  data() {
    return {
      user: {},
    };
  },
  created() {
    // const username = this.$route.params.username;
    const username = this.$store.state.user.username;

    AuthService.getUserDetails(username).then((response) => {
      this.user = response.data;
      // call your mutator to set user details in store
    });
  },
};
</script>

<style>
</style>
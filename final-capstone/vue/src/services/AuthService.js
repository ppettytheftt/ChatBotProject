import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    console.log(user)
    return axios.post('/register', user)
  },
  getUserDetails(username) {
    return axios.get(`/userdetails/${username}`)
  },

  getPathwayDetails(topic) {
    return axios.get(`/pathway/${topic}`)
  },

  updateUserAccountDetails(username, user_details) {
    return axios.put(`/updateUserDetails/${username}`, user_details)
  },

  getQuotes() {
    return axios.get('/getQuotes')
  },
  getWeather(zipCode) {
    return axios.get(`/weather/${zipCode}`)
  },
  getOpenJobs() {
    return axios.get('/openJobs')
  }
}

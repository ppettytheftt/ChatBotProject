<template>
  <div class="home">
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
      <div id="nav">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link v-bind:to="{ name: 'my-account' }">My Account</router-link>
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
      </div>
    </div>

    <div class="body-wrapper">
      <div class="welcome-wrapper">
        <h2 id="welcome-message">
          Welcome, {{ this.$store.state.user_details.firstName }}!
        </h2>
        <h6 v-if="this.$store.state.user_details.isStudent">Student</h6>
        <h6 v-else>Alumni</h6>
        <br />
        <div class="quotes">{{ this.$store.state.quotes[0].quote }}</div>
        <div class= "weather-wrapper">       
          <div class="weather">
          {{ this.$store.state.weather.nameOfLocation }},
          {{ this.$store.state.weather.stateLocated }}
          </div>
          <div class="weather">
            {{ this.localDate }}
          </div>
          <div class="weather">
            {{ this.$store.state.weather.currentTemperature }} F <br><br>
          </div>
         </div>
         <div class="usefulLinks">
           <label for="usefuleLinks">Useful Resources</label><br>
           <a href="https://sites.google.com/techelevator.com/nlrpathwayresourcepage/home" target="_blank" class="resources-link">Pathway Resources</a><br>
           <a href="https://v2-3-techelevator-book.netlify.app/" target="_blank" class="resources-link">Student Text Book</a>
         </div>


      </div>
      <!-- -->
      
      <button
        v-if="this.showStartChatBtn"
        class="chat-assistant-button"
        v-on:click="startChat"
      >
        Start Chat
      </button>
     
      <div class="chat" v-else>
        <div class="chat-container">
          <ul class="chat-box-list" ref="chatbox">
            <li class="bot">
              <span
                >{{ this.greeting1 }}
                {{ this.$store.state.user_details.firstName }},
                {{ this.greeting2 }}
              </span>
              <br />
              <p>{{mainOption}}</p>
              <p v-for="option in options" v-bind:key="option.index">
                <ul>
                  <li>
                      <span> {{ option }} </span> <br />
                  </li>
                </ul>
                
              </p>
            </li>
            <li
              class="message"
              v-for="(message, index) in messages"
              :key="index"
              :class="message.author"
            >
              <p>
                <span> {{ message.text }} </span>
              </p>
              <p>
                <span> {{ message.responseText }} </span>
              </p>
            
              
                <span>
                 <p>
                    <a v-bind:href="message.responseLink" target="_blank" >{{ message.responseLink }}</a>
                  </p>
                </span>
                
                  <img class="motivational-image" v-bind:src="message.responseImage"/>
                  <!-- <img src="../Images/robot-image.png" alt=""> -->
                  <!-- <v-img :src="message.responseImage"></v-img> -->
                  <!-- <v-img :src="require(message.responseImage)"></v-img> -->
                 <!-- <img v-img src="message.responseImage"/>   -->

               
              

              
            </li>
          </ul>
          <div class="chat-inputs">
            <input
              class="chatinputbox"
              type="text"
              v-model="message"
              @keyup.enter="sendMessage"
            />
            <button class="sendBtn" v-on:click="sendMessage">Send</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
 <!-- in Styling, float left and float right will have the messages displaying on either side, and using the dynamic class 
  messages can be put into different colors. with the sendMessage function we need to be able to send a request to the back end
  and we need to be able to get the response to put into our messages from the back end's answer.
  
  setting an overflow: scroll will keep the chatbox from distorting when there are more messages than the window can contain-->

<script>
import AuthService from "../services/AuthService.js";

export default {
  name: "home",

  data() {
    return {
      fly: false,
      date: {},
      localDate: {},
      pathwayOptions: [
        " Here are some options to choose from:",
        " * Sample Technical Questions",
        " * Sample Behavioral Questions",
        " * Cover Letter Help",
        " * Interview Attire",
      ],
      curriculumOptions: [
        " Here are some options to choose from:",
        " * Inheritance",
        " * Methods",
        " * Objects",
        " * Spring",
        " * Arrays",
        " * Classes",
      ],
      TimesTechnicalLinksShown: 0,
      TimesBehavioralLinksShown: 0,
      NumberOfYes: 0,
      NumberOfNo: 0,
      // pathwayCounter: 0,
      technicalCounter: 0,
      behavioralCounter: 0,
      currentIndex: 0,
      topic: "",
      message: "",
      messages: [],
      toBeDecidedtopic: "",
      showChatWindow: false,
      showStartChatBtn: true,
      motivationOptions: [
        " Here are some options to choose from:",
        " * Imposter Syndrome",
        " * Totally Lost",
        " * Self Doubt",
        " * Stress",
        " * Puppies",
      ],
      mainOption: "Please choose from the following options:",
      options: [
        " Pathway",
        "  Curriculum",
        " Find Open Positions",
        " Motivation",
      ],
      greeting1: "Hi ",
      greeting2: "how can I help you today?",
    };
  },

  created() {
    AuthService.getQuotes().then((response) => {
      for (let i = 0; i < 5; i++) {
        this.$store.commit("SET_QUOTES", response.data);
      }
    });

    const zip = this.$store.state.user_details.zip;
    AuthService.getWeather(zip).then((response) => {
      this.$store.commit("SET_WEATHER", response.data);

      this.date = new Date(this.$store.state.weather.localDateAndTime);
      this.localDate =
        this.date.toLocaleDateString() + " " + this.date.toLocaleTimeString();
    });

    // this.date = new Date(this.$store.state.weather.localDateAndTime);
    // this.localDate =
    //   this.date.toLocaleDateString() + " " + this.date.toLocaleTimeString();

    AuthService.getPathwayDetails("technical").then((response) => {
      if (response.status == 200) {
        this.$store.commit(
          "SET_TECHNICAL_RESPONSE_TEXT",
          response.data.responseTextList
        );
        this.$store.commit(
          "SET_TECHNICAL_RESPONSE_LINKS",
          response.data.responseLinkList
        );
      }
    });

    AuthService.getPathwayDetails("behavioral").then((response) => {
      if (response.status === 200) {
        this.$store.commit(
          "SET_BEHAVIORAL_RESPONSE_TEXT",
          response.data.responseTextList
        );
        this.$store.commit(
          "SET_BEHAVIORAL_RESPONSE_LINKS",
          response.data.responseLinkList
        );
      }
    });

    this.$store.commit("SET_CURRICULUM_OBJECT");

    AuthService.getOpenJobs().then((response) => {
      if (response.status === 200) {
        this.$store.commit("SET_OPEN_JOBS", response.data);
      }
    });
  },
  methods: {
    wonderfulDay() {
      this.messages.push({
        responseImage: "../images/Success.png",
        author: "motivation-image",
      });

      this.messages.push({
        text: "Have a wonderful day!",
        author: "bot",
      });
    },

    pathwayRoute() {
      this.topic = "pathway";
      this.messages.push({
        text: "Sure, what are you looking for in " + this.topic + "?",
        author: "bot",
      });
      for (let i = 0; i < this.pathwayOptions.length; i++) {
        this.messages.push({ text: this.pathwayOptions[i], author: "bot" });
      }
    },

    printTechnicalResponses() {
      this.currentIndex = Math.floor(Math.random() * 17);
      this.messages.push({
        text: "Here's a sample Technical Interview Question: ",
        responseText: this.$store.state.technicalResponseTextList[
          this.currentIndex
        ],
        author: "bot",
      });
      this.messages.push({
        text: "Was that helpful?",
        author: "bot",
      });
    },
    printBehavioralResponses() {
      this.currentIndex = Math.floor(Math.random() * 19);
      this.messages.push({
        text: "Here's a sample Behavioral Interview Question: ",
        responseText: this.$store.state.behavioralResponseTextList[
          this.currentIndex
        ],
        author: "bot",
      });
      this.messages.push({
        text: "Was that helpful?",
        author: "bot",
      });
    },

    startChat() {
      this.showChatWindow = true;
      this.showStartChatBtn = false;
    },

    topicToBeDecided(message) {
      var msg = message.toLowerCase();
      this.message = "";
      if (
        msg.includes("pathway") ||
        msg.includes("pthaway") ||
        msg.includes("pathawy")
      ) {
        this.pathwayRoute();
      } else if (
        msg.includes("curriculum") ||
        msg.includes("curriculim") ||
        msg.includes("cirriculum") ||
        msg.includes("home")
      ) {
        this.topic = "curriculum";

        this.messages.push({
          text: "Sure, what are you looking for in " + this.topic + "?",
          author: "bot",
        });
        for (let i = 0; i < this.curriculumOptions.length; i++) {
          this.messages.push({
            text: this.curriculumOptions[i],
            author: "bot",
          });
        }
      } else if (msg.includes("mot")) {
        this.topic = "motivation";

        for (let i = 0; i < this.motivationOptions.length; i++) {
          this.messages.push({
            text: this.motivationOptions[i],
            author: "bot",
          });
        }
      } else if (
        msg.includes("imposter") ||
        msg.includes("syndrome") ||
        msg.includes("impostor")
      ) {
        this.topic = "imposter";

        this.messages.push({
          responseImage: "../images/impostor_syndrome_1.png",
          author: "motivation-image",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (
        msg.includes("lost") ||
        msg.includes("totally") ||
        msg.includes("lsot")
      ) {
        this.topic = "lost";
        this.messages.push({
          responseImage: "../images/TotallyLost.jpg",
          author: "motivation-image",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (
        msg.includes("self") ||
        msg.includes("dou") ||
        msg.includes("slef")
      ) {
        this.topic = "doubt";
        this.messages.push({
          responseImage: "../images/SelfDoubt.jpg",
          author: "motivation-image",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("stress") || msg.includes("srtess")) {
        this.topic = "stress";
        this.messages.push({
          responseImage: "../images/Confidence.png",
          author: "motivation-image",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (
        msg.includes("puppies") ||
        msg.includes("pup") ||
        msg.includes("dog")
      ) {
        this.topic = "puppies";
        this.messages.push({
          responseImage: "../images/puppies.png",
          author: "motivation-image",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("array") || msg.includes("aray")) {
        this.topic = "array";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Arrays,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (
        msg.includes("method") ||
        msg.includes("function") ||
        msg.includes("fucniton") ||
        msg.includes("fucntion")
      ) {
        this.topic = "method";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Methods,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("object") || msg.includes("obejct")) {
        this.topic = "object";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Objects,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("spring") || msg.includes("spirng")) {
        this.topic = "spring";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Spring,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("inheritance") || msg.includes("inheritence")) {
        this.topic = "inheritance";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Inheritance,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("clas") || msg.includes("cals")) {
        this.topic = "class";

        this.messages.push({
          text: "Here is a link that may be helpful: ",
          author: "bot",
        });
        this.messages.push({
          responseLink: this.$store.state.curriculumObject.Class,
          author: "bot",
        });
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (
        msg.includes("position") ||
        msg.includes("job") ||
        msg.includes("opportunities")
      ) {
        this.topic = "job";
        this.messages.push({
          text: "Here is a list of open positions you should explore: ",
          author: "bot",
        });

        for (let i = 0; i < this.$store.state.openJobsArray.length; i++) {
          this.messages.push({
            responseLink: this.$store.state.openJobsArray[i].landingPageLink,
            author: "bot",
          });
        }
        this.messages.push({
          text:
            "I hope that was helpful, do you need help on any other topics?",
          author: "bot",
        });
      } else if (msg.includes("tech") || msg.includes("tehc")) {
        this.topic = "technical";

        this.printTechnicalResponses();
      } else if (msg.includes("behav")) {
        this.topic = "behavioral";

        this.printBehavioralResponses();
      } else if (msg.includes("cov") || msg.includes("letter")) {
        this.topic = "cover";
        AuthService.getPathwayDetails(this.topic).then((response) => {
          if (response.status === 200) {
            this.$store.commit(
              "SET_COVER_RESPONSE_LINKS",
              response.data.responseLinkList
            );
            this.messages.push({
              text: "Here's some information about cover letters: ",
              author: "bot",
            });

            for (
              let i = 0;
              i < this.$store.state.coverResponseLinkList.length;
              i++
            ) {
              this.messages.push({
                responseLink: this.$store.state.coverResponseLinkList[i],
                author: "bot",
              });
            }
            this.messages.push({
              text:
                "I hope that was helpful, do you need help on any other topics?",
              author: "bot",
            });
          }
        });
      } else if (
        msg.includes("attire") ||
        msg.includes("dress") ||
        msg.includes("business wear") ||
        msg.includes("cloth")
      ) {
        this.topic = "attire";
        AuthService.getPathwayDetails(this.topic).then((response) => {
          if (response.status === 200) {
            this.$store.commit(
              "SET__ATTIRE_RESPONSE_LINKS",
              response.data.responseLinkList
            );
            this.messages.push({
              text: "Here's some information about business attire: ",
              author: "bot",
            });

            for (
              let i = 0;
              i < this.$store.state.attireResponseLinkList.length;
              i++
            ) {
              this.messages.push({
                responseLink: this.$store.state.attireResponseLinkList[i],
                author: "bot",
              });
            }
            this.messages.push({
              text:
                "I hope that was helpful, do you need help on any other topics?",
              author: "bot",
            });
          }
        });
      }
    },

    sendMessage() {
      this.messages.push({
        text: this.message,
        author: "client",
      });

      /* this.$nextTick(() => {
        this.$refs.chatbox.scrollTop = this.$refs.chatbox.scrollHeight;
      });*/

      this.$nextTick(() => {
        this.$refs.chatbox.scrollTop = this.$refs.chatbox.scrollHeight;
      });

      if (this.message.toLowerCase().includes("no")) {
        if (this.topic === "behavioral") {
          this.NumberOfNo++;
          if (this.NumberOfYes == 0 && this.NumberOfNo == 1) {
            this.TimesBehavioralLinksShown++;
            if (this.TimesBehavioralLinksShown == 1) {
              //Make a call using AuthService to fetch links from general pathway folder
              AuthService.getPathwayDetails("general").then((response) => {
                if (response.status == 200) {
                  this.$store.commit(
                    "SET_GENERAL_RESPONSE_LINK_LIST",
                    response.data.responseLinkList
                  );
                }
              });
              /* Above step is done so that we can avoid waiting when we have to show the general pathway link*/

              this.messages.push({
                text: "Here are a few links to refer to:",
                author: "bot",
              });
              for (
                let i = 0;
                i < this.$store.state.behavioralResponseLinkList.length;
                i++
              ) {
                this.messages.push({
                  responseLink: this.$store.state.behavioralResponseLinkList[i],
                  author: "bot",
                });
              }
            } else if (this.TimesBehavioralLinksShown > 1) {
              this.messages.push({
                responseLink: this.$store.state.generalResponseLinkList[0],
                author: "bot",
              }); //end of push
            }
            this.messages.push({
              text: "Would you like help with other topics?",
              author: "bot",
            });
            for (let i = 0; i < this.pathwayOptions.length; i++) {
              this.messages.push();
            }
          } else if (this.NumberOfYes == 1 && this.NumberOfNo == 1) {
            this.wonderfulDay();
            this.NumberOfYes = 0;
          } else if (this.NumberOfNo == 2 && this.NumberOfYes == 0) {
            this.wonderfulDay();
            this.NumberOfYes = 0;
            this.NumberOfNo = 0;
            this.TimesTechnicalLinksShown = 0;
            this.TimesBehavioralLinksShown = 0;
          }
        } //end of if block for behavioral
        else if (this.topic === "technical") {
          this.NumberOfNo++;
          if (this.NumberOfYes == 0 && this.NumberOfNo == 1) {
            this.TimesTechnicalLinksShown++;
            if (this.TimesTechnicalLinksShown == 1) {
              //Make a call using AuthService to fetch links from general pathway folder
              AuthService.getPathwayDetails("general").then((response) => {
                if (response.status == 200) {
                  this.$store.commit(
                    "SET_GENERAL_RESPONSE_LINK_LIST",
                    response.data.responseLinkList
                  );
                }
              });
              /* Above step is done so that we can avoid waiting when we have to show the general pathway link*/

              this.messages.push({
                text: "Here are a few links to refer to:",
                author: "bot",
              });
              for (
                let i = 0;
                i < this.$store.state.technicalResponseLinkList.length;
                i++
              ) {
                this.messages.push({
                  responseLink: this.$store.state.technicalResponseLinkList[i],
                  author: "bot",
                });
              }
            } else if (this.TimesTechnicalLinksShown > 1) {
              this.messages.push({
                responseLink: this.$store.state.generalResponseLinkList[0],
                author: "bot",
              }); //end of push
            }
            this.messages.push({
              text: "Would you like help with other topics?",
              author: "bot",
            });
            for (let i = 0; i < this.pathwayOptions.length; i++) {
              this.messages.push();
            }
          } else if (this.NumberOfYes == 1 && this.NumberOfNo == 1) {
            this.wonderfulDay();
            this.NumberOfYes = 0;
          } else if (this.NumberOfNo == 2 && this.NumberOfYes == 0) {
            this.wonderfulDay();
          }
        } else if (
          this.topic === "cover" ||
          this.topic === "attire" ||
          this.topic === "method" ||
          this.topic === "array" ||
          this.topic === "object" ||
          this.topic === "spring" ||
          this.topic === "inheritance" ||
          this.topic === "class" ||
          this.topic === "job" ||
          this.topic === "imposter" ||
          this.topic === "doubt" ||
          this.topic === "lost" ||
          this.topic === "stress" ||
          this.topic === "puppies"
        ) {
          if (this.message.toLowerCase().includes("no")) {
            this.wonderfulDay();
          } else if (this.message.toLowerCase().includes("yes")) {
            for (let i = 0; i < this.pathwayOptions.length; i++) {
              this.messages.push({
                text: this.pathwayOptions[i],
                author: "bot",
              });
            }
          }
        }
      } //End of if block for 'no'

      if (
        (this.message.toLowerCase().includes("yes") ||
          this.message.toLowerCase().includes("yea") ||
          this.message.toLowerCase().includes("yeah") ||
          this.message.toLowerCase().includes("ya") ||
          this.message.toLowerCase().includes("sure")) &&
        (this.topic === "behavioral" || this.topic === "technical")
      ) {
        this.NumberOfYes++;
        if (this.NumberOfYes == 1 && this.NumberOfNo == 0) {
          this.messages.push({
            text: "Cool, anything else I can help you with today?",
            author: "bot",
          });
          this.message = "";
        } else if (
          this.NumberOfYes == 2 ||
          (this.NumberOfYes == 1 && this.NumberOfNo == 1)
        ) {
          for (let i = 0; i < this.options.length; i++) {
            this.messages.push({ text: this.options[i], author: "bot" });
            this.NumberOfYes = 0;
            this.NumberOfNo = 0;
          }
          this.message = "";
        } //end of else if
      } //end of if block
      else if (
        (this.message.toLowerCase().includes("yes") ||
          this.message.toLowerCase().includes("yea") ||
          this.message.toLowerCase().includes("yeah") ||
          this.message.toLowerCase().includes("ya") ||
          this.message.toLowerCase().includes("sure")) &&
        (this.topic === "cover" ||
          this.topic === "attire" ||
          this.topic === "method" ||
          this.topic === "array" ||
          this.topic === "object" ||
          this.topic === "spring" ||
          this.topic === "inheritance" ||
          this.topic === "class" ||
          this.topic === "job" ||
          this.topic === "imposter" ||
          this.topic === "doubt" ||
          this.topic === "lost" ||
          this.topic === "stress" ||
          this.topic === "puppies")
      ) {
        this.message = "";
        for (let i = 0; i < this.options.length; i++) {
          this.messages.push({
            text: this.options[i],
            author: "bot",
          });
        }
      } else {
        this.topicToBeDecided(this.message);
      }
    }, //end of sendMessage,
  },
};
</script>

<style scoped>
.motivational-image {
  width: 475px;
  border-radius: 15px;
  padding-left: 5px;
  margin-top: 5px;
  margin-bottom: 5px;
}

.usefulLinks {
  background-color: rgba(201, 76, 76, 0.3);
  background-position: 65px;
  margin-right: 15px;
  padding-top: 10px;
  padding-right: 10px;
  padding-left: 20px;
  padding-bottom: 20px;
  text-align: center;
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

.body-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 100vh;
}

.head-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
}

.chat {
  border: 1px solid gray;
  width: 50vw;
  height: 80vh;
  border-radius: 4px;
  background-color: white;
  margin-top: 3em;
}

.chat-container {
  width: 100%;
  height: 100%;
  margin-top: -30px;
}

.resources-link {
  font-family: Poppins, sans-serif;
  font-size: 18px;
  color: #caf0f8;
  text-decoration: none;
}

.resources-link:hover {
  color: #03045e;
  text-decoration: underline;
}

.chat-box-list {
  background-color: #e9ecef;
  display: flex;
  flex-direction: column;
  list-style-type: none;
  overflow: scroll;
  height: 90%;
  padding-left: 5px;
  padding-right: 3px;
}

.chat-inputs {
  display: flex;
  flex-direction: row;
}

.sendBtn {
  margin-top: 5px;
  height: 53px;
}

.chatinputbox {
  width: 100%;
  height: 50px;
  border: 1px solid gray;
  border-left: none;
  border-bottom: none;
  border-right: none;
  border-bottom-left-radius: 4px;
  margin: 0;
  margin-top: 6px;
  padding-left: 15px;
  padding-bottom: 0;
  padding-top: 0;
}

.chatinputbox > input {
  padding-left: 10px;
}

.welcome-wrapper {
  display: flex;
  flex-direction: column;
  text-transform: capitalize;
  font-family: "Poppins", sans-serif;
  margin-left: 10px;
  width: 40vw;
}

.welcome-wrapper h2 {
  font-size: 48px;
  color: #343a40;
}

.welcome-wrapper h6 {
  font-size: 25px;
  margin-top: -35px;
  color: #343a40;
}

button {
  width: 180px;
  height: 50px;
  border: none;
  color: antiquewhite;
  background-color: #0e3a8d;
  font-family: "Poppins", sans-serif;
  font-size: 25px;
  letter-spacing: 4px;
  border-bottom-right-radius: 8px;
  outline: none;
  transition: ease-in-out 0.8s;
}

button:hover {
  background-color: #0076b6;
}

#nav > a {
  font-family: "Poppins", sans-serif;
  font-size: 25px;
  letter-spacing: 5px;
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
  color: #343a40;
  background-image: linear-gradient(to bottom left, #34a0a4, #52b69a);
}
#nav {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 70%;
}

.chat-assistant-button {
  width: 300px;
  height: 80px;
  border-radius: 8px;
  margin-top: 4%;
  position: fixed;
  right: 10px;
  bottom: 10px;
  font-size: 37px;
  color: antiquewhite;
  background-color: #0e3a8d;
  transition: ease-out 0.8s;
}

.chat-assistant-botton :hover {
  color: #343a40;
  background-image: linear-gradient(to bottom left, #168aad, #1a759f, #1e6091);
}

.bot span {
  float: left;
  margin: 3px;
  font-family: "Open Sans", sans-serif;
  font-size: 18px;
}
.bot p {
  font-family: "Open Sans", sans-serif;
  font-size: 18px;
}

.bot {
  background-image: linear-gradient(to bottom right, #168aad, #34a0a4);
  width: 80%;
  border-radius: 10px 10px 10px 1px;
  padding-bottom: 5px;
  padding-left: 5px;
  margin-top: 5px;
  margin-bottom: 2px;
  overflow-wrap: break-word;
  hyphens: auto;
  word-break: break-all;
}
.client p {
  font-family: "Open Sans", sans-serif;
  font-size: 18px;
}

.client span {
  float: right;
}

.client {
  background-image: linear-gradient(to bottom right, #76c893, #99d98c);
  margin-left: 30%;
  margin-top: 5px;
  margin-bottom: 5px;
  padding-bottom: 10px;
  padding-right: 20px;
  margin-right: 3px;
  overflow-wrap: break-word;
  hyphens: auto;
  word-break: break-all;
  padding-left: 5px;
  border-radius: 10px 10px 1px 10px;
}

.quotes {
  font-family: Open Sans, Oswald, Poppins;
  word-break: break-word;
  overflow-wrap: break-word;
  margin-right: 4px;
  font-size: 18px;
  font-weight: bold;
}
.weather-wrapper {
  margin-top: 10px;
}
</style>


import React from 'react';
import StoryFrame from './StoryFrame';
// import styles from "./StoryFormParts/StoryFormParts.module.scss";
import {FieldArray, Form, Formik } from 'formik';
import FormField from "../../FormField";
// import AlertMessage from './../../ui/AlertMessage/index';
// import Button from './../../ui/Button/index';
import { initialStoryValues } from "../../../utils/constants/initialValues";
// import styles from "../StoryFormParts/StoryFormParts.module.scss";


const StoryCard = ({ story }) => {
  // const [success, setSuccess] = React.useState(true)
  return (
    <div>
      <div>
        <Formik
            enableReinitialize
            initialValues={initialStoryValues}
          >
            {() => (
              <Form>
                <FieldArray name="stories">
                {(props) => (
                    <div style={{ display: "flex", alignItems: "center" }}>
                      <div style={{ width: "70%" }}> {/* Левая часть контейнера с полями ввода */}
                        <div className='frame' style={{ marginBottom: "10px" }}>
                          <p>Банк</p>
                          <div style={{ width: "100%" }}>
                            <FormField
                              name="fee"
                              change
                              as={"input"}
                              type="text"
                              {...props}
                            />
                          </div>
                        </div>
                        <div className='frame'>
                          <p>Preview Title:</p>
                          <div>
                            <FormField
                              change
                              as={"input"}
                              type="text"
                              {...props}
                            />
                          </div>
                        </div>
                      </div>
                      <div style={{ width: "25%", marginLeft: "auto", float: "right" }}> {/* Правая часть контейнера с картинкой */}
                        <img src={"http://localhost:8080" + story.previewUrl} alt="Story Frame" style={{ width: "100%" }} />
                      </div>
                  </div>
                  )}
                </FieldArray>
              </Form>
            )}
          </Formik>
        <p>previewGradient: {"asdasd"}</p>
        {/* Другие поля для отображения */}
      </div>
      <div>
        <h3>Story Frames:</h3>
        <ul className='stories'>
            {story.storyFrames.map((value, index) => (
                <li key={index}>
                <details>
                <summary>{story.previewTitle+" " +story.id}</summary>
                <StoryFrame key={index} frame={value} />
                </details>
                </li>
            ))}
            </ul>
      </div>
    </div>
  );
}

export default StoryCard;

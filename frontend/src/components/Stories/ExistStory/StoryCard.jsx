import React from 'react';
import StoryFrame from './StoryFrame';
// import styles from "./StoryFormParts/StoryFormParts.module.scss";
import {FieldArray, Form, Field, Formik } from 'formik';
import FormField from "../../FormField";
// import AlertMessage from './../../ui/AlertMessage/index';
// import Button from './../../ui/Button/index';
import { initialStoryValues } from "../../../utils/constants/initialValues";
// import styles from "../StoryFormParts/StoryFormParts.module.scss";


const StoryCard = ({ storyIndex, story}) => {
  // const [success, setSuccess] = React.useState(true)
  return (
    <div>
      <div>
        <Formik
          enableReinitialize
          initialValues={{
            bankId: initialStoryValues.bankId,
            previewTitle: initialStoryValues.previewTitle,
            // Другие начальные значения
          }}
          onSubmit={(values) => {
            // Обработка отправки формы
            console.log(values);
          }}
        >
          {({ values, handleChange }) => (
            <Form>
              <FieldArray name={`stories.${storyIndex}.storyFrames`}>
                {() => (
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <div style={{ width: "70%" }}> {/* Левая часть контейнера с полями ввода */}
                      <div className='frame' style={{ marginBottom: "10px" }}>
                        <p>Банк</p>
                        <div style={{ width: "100%" }}>
                          <Field
                            name="bankId"
                            as={FormField}
                            type="text"
                            value={values.bankId}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <div className='frame'>
                        <p>Preview Title:</p>
                        <div>
                          <Field
                            name="previewTitle"
                            as={FormField}
                            type="text"
                            value={values.previewTitle}
                            onChange={handleChange}
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
      </div>
      <div>
        <h3>Story Frames:</h3>
        <ul>
          {story.storyFrames.map((value, index) => (
            <li className='listFrame stories' key={index}>
              <details>
                <summary>{story.previewTitle + " " + story.id}</summary>
                <StoryFrame key={index} frame={value} frameIndex={index} storyIndex={storyIndex} />
              </details>
              {console.log(value)}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default StoryCard;

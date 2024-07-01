import React from 'react';
import StoryFrame from './StoryFrame';
// import styles from "./StoryFormParts/StoryFormParts.module.scss";
import {FieldArray, Form, Field, Formik } from 'formik';
import FormField from "../../FormField";
// import AlertMessage from './../../ui/AlertMessage/index';
// import Button from './../../ui/Button/index';
import { initialStoryValues } from "../../../utils/constants/initialValues";
// import styles from "../StoryFormParts/StoryFormParts.module.scss";
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import Button from '../.././ui/Button';

const StoryCard = ({ storyIndex, story}) => {
  // const [success, setSuccess] = React.useState(true)
  return (
    <div>
      <div>
        <Formik
          enableReinitialize
          initialValues={{
            previewTitle: initialStoryValues.previewTitle,
            previewTitleColor: initialStoryValues.previewTitleColor,
            previewGradient: initialStoryValues.previewGradient,
          }}
          onSubmit={(values) => {
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
                        <p>Заголовок</p>
                        <div style={{ width: "100%" }}>
                          <Field
                            name="previewTitle"
                            as={FormField}
                            type="text"
                            value={values.previewTitle}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <div className='frame'>
                        <p>previewTitleColor</p>
                        <div>
                          <Field
                            name="previewTitleColor"
                            as={FormField}
                            type="text"
                            value={values.previewTitleColor}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <div className='frame'>
                        <p>previewGradient</p>
                        <div>
                          <FormField
                            name={`previewGradient`}
                            value={values.previewGradient}
                            onChange={handleChange}
                            as="select"
                            options={gradientOptions}
                          />
                        </div>
                      </div>
                    </div>
                    <div style={{width: "25%", marginLeft: "auto", float: "right" }}> {/* Правая часть контейнера с картинкой */}
                      <img src={"http://localhost:8080" + story.previewUrl} alt="Story Frame" style={{ width: "100%" }} />
                      <Button
                        text="Изменить"
                        type="button"
                        color="green"
                        icon={<ArrowIcon width="12px" height="12px" />}
                      />
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
            <li className='listFrame' key={index}>
              <details className="item-card">
                <summary className="item-card__summary">
                  <p className="item-card__title">{value.title}</p>
                  <div className="item-card__buttons">
                    <div className="item-card__button--delete">
                      <Button
                            text="Удалить"
                            type="button"
                            color="red"
                            icon={<ArrowIcon width="12px" height="12px" />}
                          />
                    </div>
                  </div>
                </summary>
                <div className="item-card__content">
                <StoryFrame key={index} frame={value} frameIndex={index} storyIndex={storyIndex} />
                </div>
              </details>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default StoryCard;

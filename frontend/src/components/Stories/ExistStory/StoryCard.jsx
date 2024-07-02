import React, { useState, useEffect } from 'react';
import StoryFrame from './StoryFrame';
import {FieldArray, Form, Field, Formik } from 'formik';
import FormField from "../../FormField";
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import Button from '../.././ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { deleteFrame, updateStory } from './../../../api/stories';

const StoryCard = ({ storyIndex, story, platform}) => {
   const [frames, setFrames] = useState(story.storyFrames);

  const handleOnSubmit = async (story, frame, platform) => {
    const success = await deleteFrame(story, frame, platform);
    if (success) {
      setFrames(prevFrames => prevFrames.filter(item => item.id !== frame.id));
    }
  };

  useEffect(() => {
    if (story && story.storyFrames) {
      setFrames(story.storyFrames);
    }
  }, [story]);

  return (
    <div>
      <div>
        <Formik
          enableReinitialize
          initialValues={{
            previewTitle: story.previewTitle,
            previewTitleColor: story.previewTitleColor,
            previewGradient: story.previewGradient,
          }}
        >
          {({ values, handleChange }) => (
            <Form>
              <FieldArray name={`stories.${storyIndex}.storyFrames`}>
                {() => (
                  <div className='row' style={{ display: "flex", alignItems: "center" }}>
                    <div style={{ width: "70%" }}> {/* Левая часть контейнера с полями ввода */}
                      <div className='frame' style={{ marginBottom: "10px" }}>
                        <h3 style={{paddingRight: "10px"}}>Заголовок</h3>
                        <div style={{ width: "100%" }}>
                          <Field
                            name={`previewTitle`}
                            as={FormField}
                            type="text"
                            value={values.previewTitle}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <FormField
                          name={`previewTitleColor`}
                          labelTitle={"Цвет заголовка"}
                          value={values.previewTitleColor}
                          component={ColorPicker}
                          onChange={handleChange}
                        />
                      <div className='frame'>
                        <div>
                          <FormField
                            name={`previewGradient`}
                            labelTitle="Градиент"
                            value={values.previewGradient}
                            onChange={handleChange}
                            as="select"
                            options={gradientOptions}
                          />
                        </div>
                      </div>
                    </div>
                    <div style={{width: "30%", marginLeft: "auto", float: "right" }}> {/* Правая часть контейнера с картинкой */}
                      <img src={"http://localhost:8080" + story.previewUrl} alt="Story Frame" style={{ width: "100%" }} />
                      <Button
                        handleOnClick={() => updateStory(story, values, platform)}
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
          {frames.map((value, index) => (
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
                            handleOnClick={() => handleOnSubmit(story, value, platform)}
                          />
                    </div>
                  </div>
                </summary>
                <div className="item-card__content">
                <StoryFrame
                 key={index}
                 frame={value}
                 frameIndex={index}
                 storyIndex={storyIndex}
                 story={story}
                 platform={platform} 
                />
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

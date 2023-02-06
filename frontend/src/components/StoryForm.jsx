import React from 'react';
import { Formik, Form, FieldArray } from 'formik';

import { storyValidationSchema } from '../utils/helpers/validation';
import { initialStoryValues, initialStoryFrame } from '../utils/constants/initialValues';
import { banks } from '../utils/constants/banks';

import Button from './Button';
import PreviewFields from './StoryFormParts/PreviewFields';
import FrameFields from './StoryFormParts/FrameFields';
import FormField from './FormField';

const maxFrames = 6;

const StoryForm = () => {
  return (
    <div>
      <Formik
        enableReinitialize
        initialValues={initialStoryValues}
        validationSchema={storyValidationSchema}
        onSubmit={(values) => {
          const data = JSON.stringify(values, null, 2);
          // ОТПРАВИТЬ
          console.log(JSON.stringify(values, null, 2));
        }}>
        {(props) => (
          <Form>
            <div className="input_field">
              <FormField
                labelTitle={'Банк'}
                name={`bankId`}
                as="select"
                options={banks.map((bank) => {
                  return { value: bank.id, name: bank.name };
                })}
                errors={props.errors}
                touched={props.touched}
              />
            </div>
            <h2>Превью</h2>
            <FieldArray name="stories">
              {() => (
                <>
                  {props.values.stories.map((story, indexS) => (
                    <>
                      <PreviewFields
                        storyIndex={indexS}
                        setFieldValue={props.setFieldValue}
                        errors={props.errors}
                        touched={props.touched}
                      />
                      <br />
                      <h2>Карточки</h2>
                      <FieldArray name={`stories.${indexS}.storyFrames`}>
                        {({ remove, push }) => (
                          <>
                            {story.storyFrames.map((storyFrame, index) => (
                              <FrameFields
                                storyIndex={indexS}
                                frameIndex={index}
                                framesCount={story.storyFrames.length}
                                setFieldValue={props.setFieldValue}
                                frameJson={storyFrame}
                                remove={remove}
                                errors={props.errors}
                                touched={props.touched}
                              />
                            ))}
                            {story.storyFrames.length < maxFrames && (
                              <Button
                                text="Добавить"
                                type="button"
                                color="green"
                                icon={
                                  <svg
                                    fill="#000000"
                                    version="1.1"
                                    id="Capa_1"
                                    width="12px"
                                    height="12px"
                                    viewBox="0 0 45.402 45.402">
                                    <g>
                                      <path
                                        d="M41.267,18.557H26.832V4.134C26.832,1.851,24.99,0,22.707,0c-2.283,0-4.124,1.851-4.124,4.135v14.432H4.141
                            c-2.283,0-4.139,1.851-4.138,4.135c-0.001,1.141,0.46,2.187,1.207,2.934c0.748,0.749,1.78,1.222,2.92,1.222h14.453V41.27
                            c0,1.142,0.453,2.176,1.201,2.922c0.748,0.748,1.777,1.211,2.919,1.211c2.282,0,4.129-1.851,4.129-4.133V26.857h14.435
                            c2.283,0,4.134-1.867,4.133-4.15C45.399,20.425,43.548,18.557,41.267,18.557z"
                                      />
                                    </g>
                                  </svg>
                                }
                                handleOnClick={() => push(initialStoryFrame)}
                              />
                            )}
                          </>
                        )}
                      </FieldArray>
                    </>
                  ))}
                </>
              )}
            </FieldArray>
            <Button text="Отправить" type="submit" color="red" />
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default StoryForm;

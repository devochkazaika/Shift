import React from 'react';
import { Formik, Form, FieldArray } from 'formik';

import PreviewForm from './PreviewForm';
import FrameForm from './FrameForm';

const initialStoryFrame = {
  title: '',
  text: '',
  textColor: '#ffffffff',
  pictureUrl: '',
  linkText: '',
  linkUrl: '',
  buttonVisible: true,
  buttonText: '',
  buttonTextColor: '#ffffffff',
  buttonBackgroundColor: '#ffffffff',
  buttonUri: '',
  gradient: 'EMPTY',
};

const initialValues = {
  stories: [
    {
      previewTitle: '',
      previewTitleColor: '#ffffffff',
      previewUrl: '',
      previewGradient: 'EMPTY',
      storyFrames: [initialStoryFrame],
    },
  ],
};

const StoryForm = () => {
  return (
    <div>
      <h2>История</h2>
      <Formik
        enableReinitialize
        initialValues={initialValues}
        onSubmit={(values) => {
          console.log(JSON.stringify(values, null, 2));
        }}>
        {(props) => (
          <Form>
            <FieldArray name="stories">
              {() => (
                <>
                  {props.values.stories.map((story, indexS) => (
                    <>
                      <PreviewForm storyIndex={indexS} />
                      <br />
                      <h2>Карточки</h2>

                      <FieldArray name={`stories.${indexS}.storyFrames`}>
                        {({ remove, push }) => (
                          <>
                            {story.storyFrames.map((storyFrame, index) => (
                              <FrameForm
                                storyIndex={indexS}
                                frameIndex={index}
                                setFieldValue={props.setFieldValue}
                                frameJson={storyFrame}
                                remove={remove}
                              />
                            ))}
                            <button
                              type="button"
                              className="secondary"
                              onClick={() => {
                                if (props.values.stories[indexS].storyFrames.length <= 5) {
                                  push(initialStoryFrame);
                                }
                              }}>
                              + Добавить
                            </button>
                          </>
                        )}
                      </FieldArray>
                    </>
                  ))}
                </>
              )}
            </FieldArray>
            <button type="submit">Invite</button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default StoryForm;

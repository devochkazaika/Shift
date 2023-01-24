import React from 'react';

import { Formik, Field, Form, ErrorMessage, FieldArray } from 'formik';

const initialValues = {
  stories: [
    {
      previewTitle: '',
      previewTitleColor: '',
      previewUrl: '',
      storyFrames: [
        {
          title: '',
          text: '',
          textColor: '',
          pictureUrl: '',
          linkText: '',
          linkUrl: '',
          buttonVisible: true,
          buttonText: '',
          buttonTextColor: '',
          buttonBackgroundColor: '',
          buttonUri: '',
        },
      ],
    },
  ],
};

const StoryForm = () => {
  return (
    <div>
      <h1>Invite friends</h1>
      <Formik
        initialValues={initialValues}
        onSubmit={(values) => {
          console.log(JSON.stringify(values, null, 2));
        }}>
        {({ values }) => (
          <Form>
            <FieldArray name="stories">
              {({ arrayHelpers }) => (
                <>
                  {values.stories.map((story, indexS) => (
                    <>
                      <label htmlFor={`stories.${indexS}.previewTitle`}>Заголовок</label>
                      <Field name={`stories.${indexS}.previewTitle`} placeholder="" type="text" />
                      <FieldArray name={`stories.${indexS}.storyFrames`}>
                        {({ insert, remove, push }) => (
                          <>
                            {story.storyFrames.map((storyFrame, index) => (
                              <div className="row" key={index}>
                                <div className="col">
                                  <label htmlFor={`stories.${indexS}.storyFrames.${index}.title`}>
                                    Заголовок
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.title`}
                                    placeholder=""
                                    type="text"
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.title`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>
                                <div className="col">
                                  <label htmlFor={`stories.${indexS}.storyFrames.${index}.text`}>
                                    Текст
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.text`}
                                    placeholder=""
                                    type="text"
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.text`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>
                                <div className="col">
                                  <button
                                    type="button"
                                    className="secondary"
                                    onClick={() => remove.splice(index, 1)}>
                                    X
                                  </button>
                                </div>
                              </div>
                            ))}
                            <button
                              type="button"
                              className="secondary"
                              onClick={() => {
                                if (values.stories[indexS].storyFrames.length <= 5) {
                                  push({
                                    title: '',
                                    text: '',
                                    textColor: '',
                                    pictureUrl: '',
                                    linkText: '',
                                    linkUrl: '',
                                    buttonVisible: true,
                                    buttonText: '',
                                    buttonTextColor: '',
                                    buttonBackgroundColor: '',
                                    buttonUri: '',
                                  });
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

// {story.storyFrames.map((frame, index) => (
//     <FieldArray name={`stories.${index}.storyFrames`}>
//       {({ insert, remove, push }) => (
//         <div>
//   <div className="row" key={index}>
//     <div className="col">
//       <label htmlFor={`stories.${indexS}.storyFrames.${index}.title`}>
//         Заголовок
//       </label>
//       <Field
//         name={`stories.${indexS}.storyFrames.${index}.title`}
//         placeholder=""
//         type="text"
//       />
//       <ErrorMessage
//         name={`stories.${indexS}.storyFrames.${index}.title`}
//         component="div"
//         className="field-error"
//       />
//     </div>
//     <div className="col">
//       <label htmlFor={`stories.${indexS}.storyFrames.${index}.text`}>
//         Текст
//       </label>
//       <Field
//         name={`stories.${indexS}.storyFrames.${index}.text`}
//         placeholder=""
//         type="text"
//       />
//       <ErrorMessage
//         name={`stories.${indexS}.storyFrames.${index}.text`}
//         component="div"
//         className="field-error"
//       />
//     </div>
//     <div className="col">
//       <button
//         type="button"
//         className="secondary"
//         onClick={() => remove.splice(index, 1)}>
//         X
//       </button>
//     </div>
//   </div>

//   <button
//     type="button"
//     className="secondary"
//     onClick={() => {
//       push({
//         title: '',
//         text: '',
//         textColor: '',
//         pictureUrl: '',
//         linkText: '',
//         linkUrl: '',
//         buttonVisible: true,
//         buttonText: '',
//         buttonTextColor: '',
//         buttonBackgroundColor: '',
//         buttonUri: '',
//       });
//       console.log(values);
//     }}>
//     + Добавить
//   </button>
//         </div>
//       )}
//     </FieldArray>
//   ))}

{
  /* <Form>
            {values.stories.map((story, indexS) => (
              <FieldArray name="stories">
                {({ arrayHelpers }) => (
                  <>
                    <label htmlFor={`stories.${indexS}.previewTitle`}>Заголовок</label>
                    <Field name={`stories.${indexS}.previewTitle`} placeholder="" type="text" />
                    {story.storyFrames.map((frame, index) => (
                      <FieldArray name={`stories.${index}.storyFrames`}>
                        {({ insert, remove, push }) => (
                          <div>
                            {story.storyFrames.map((storyFrame, index) => (
                              <div className="row" key={index}>
                                <div className="col">
                                  <label htmlFor={`stories.${indexS}.storyFrames.${index}.title`}>
                                    Заголовок
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.title`}
                                    placeholder=""
                                    type="text"
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.title`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>
                                <div className="col">
                                  <label htmlFor={`stories.${indexS}.storyFrames.${index}.text`}>
                                    Текст
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.text`}
                                    placeholder=""
                                    type="text"
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.text`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>
                                <div className="col">
                                  <button
                                    type="button"
                                    className="secondary"
                                    onClick={() => remove.splice(index, 1)}>
                                    X
                                  </button>
                                </div>
                              </div>
                            ))}
                            <button
                              type="button"
                              className="secondary"
                              onClick={() =>
                                push({
                                  title: '',
                                  text: '',
                                  textColor: '',
                                  pictureUrl: '',
                                  linkText: '',
                                  linkUrl: '',
                                  buttonVisible: true,
                                  buttonText: '',
                                  buttonTextColor: '',
                                  buttonBackgroundColor: '',
                                  buttonUri: '',
                                })
                              }>
                              + Добавить
                            </button>
                          </div>
                        )}
                      </FieldArray>
                    ))}
                  </>
                )}
              </FieldArray>
            ))}
            <button type="submit">Invite</button>
          </Form> */
}

{
  /* <Form>
            {values.stories[0].storyFrames.map((frame, index) => (
              <FieldArray name={`stories.${0}.storyFrames`}>
                {({ insert, remove, push }) => (
                  <div>
                    <div className="row" key={index}>
                      <div className="col">
                        <label htmlFor={`stories.${0}.storyFrames.${index}.title`}>Заголовок</label>
                        <Field
                          name={`stories.${0}.storyFrames.${index}.title`}
                          placeholder=""
                          type="text"
                        />
                        <ErrorMessage
                          name={`stories.${0}.storyFrames.${index}.title`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <div className="col">
                        <label htmlFor={`stories.${0}.storyFrames.${index}.text`}>Текст</label>
                        <Field
                          name={`stories.${0}.storyFrames.${index}.text`}
                          placeholder=""
                          type="text"
                        />
                        <ErrorMessage
                          name={`stories.${0}.storyFrames.${index}.text`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <div className="col">
                        <button
                          type="button"
                          className="secondary"
                          onClick={() => remove(index, 1)}>
                          X
                        </button>
                      </div>
                    </div>
                    <button
                      type="button"
                      className="secondary"
                      onClick={() =>
                        push({
                          title: '',
                          text: '',
                          textColor: '',
                          pictureUrl: '',
                          linkText: '',
                          linkUrl: '',
                          buttonVisible: true,
                          buttonText: '',
                          buttonTextColor: '',
                          buttonBackgroundColor: '',
                          buttonUri: '',
                        })
                      }>
                      + Добавить
                    </button>
                  </div>
                )}
              </FieldArray>
            ))}
            <button type="submit">Invite</button>
          </Form> */
}

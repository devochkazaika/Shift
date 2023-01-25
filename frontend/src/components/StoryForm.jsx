import React from 'react';

import { Formik, Field, Form, ErrorMessage, FieldArray } from 'formik';

import ColorPicker from './ColorPicker';

const initialValues = {
  stories: [
    {
      previewTitle: '',
      previewTitleColor: '#ffffffff',
      previewUrl: '',
      previewGradient: 'EMPTY',
      storyFrames: [
        {
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
        },
      ],
    },
  ],
};

const StoryForm = () => {
  console.log(1);
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
              {({ arrayHelpers }) => (
                <>
                  {props.values.stories.map((story, indexS) => (
                    <>
                      <div>
                        <label htmlFor={`stories.${indexS}.previewTitle`}>Заголовок истории</label>
                        <Field name={`stories.${indexS}.previewTitle`} placeholder="" type="text" />
                        <ErrorMessage
                          name={`stories.${indexS}.previewTitle`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <div>
                        <label htmlFor={`stories.${indexS}.previewTitleColor`}>Цвет текста</label>
                        <Field
                          name={`stories.${indexS}.previewTitleColor`}
                          component={ColorPicker}
                          fieldName={`stories.${indexS}.previewTitleColor`}
                        />

                        <ErrorMessage
                          name={`stories.${indexS}.previewTitleColor`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <div>
                        <label htmlFor={`stories.${indexS}.previewUrl`}>Путь до картинки</label>
                        <Field name={`stories.${indexS}.previewUrl`} placeholder="" type="text" />
                        <ErrorMessage
                          name={`stories.${indexS}.previewUrl`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <div>
                        <label htmlFor={`stories.${indexS}.previewGradient`}>
                          Градиент истории
                        </label>
                        <Field as="select" name={`stories.${indexS}.previewGradient`}>
                          <option value="EMPTY">Нет</option>
                          <option value="FULL">Поверх всего изображения</option>
                        </Field>
                        <ErrorMessage
                          name={`stories.${indexS}.previewGradient`}
                          component="div"
                          className="field-error"
                        />
                      </div>
                      <br />
                      <h2>Карточки</h2>

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
                                  <label
                                    htmlFor={`stories.${indexS}.storyFrames.${index}.textColor`}>
                                    Цвет текста
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.textColor`}
                                    component={ColorPicker}
                                    fieldName={`stories.${indexS}.storyFrames.${index}.textColor`}
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.textColor`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>
                                <div className="col">
                                  <label
                                    htmlFor={`stories.${indexS}.storyFrames.${index}.pictureUrl`}>
                                    Путь до картинки
                                  </label>
                                  <Field
                                    name={`stories.${indexS}.storyFrames.${index}.pictureUrl`}
                                    placeholder=""
                                    type="text"
                                  />
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.pictureUrl`}
                                    component="div"
                                    className="field-error"
                                  />
                                </div>

                                <div role="group" aria-labelledby="my-radio-group">
                                  <label>
                                    <Field
                                      type="radio"
                                      name={`stories.${indexS}.storyFrames.${index}.buttonVisible`}
                                      value="true"
                                      onChange={(e) => {
                                        props.setFieldValue(
                                          `stories.${indexS}.storyFrames.${index}.buttonVisible`,
                                          true,
                                        );
                                      }}
                                      checked={storyFrame.buttonVisible}
                                    />
                                    Кнопка
                                  </label>
                                  <label>
                                    <Field
                                      type="radio"
                                      name={`stories.${indexS}.storyFrames.${index}.buttonVisible`}
                                      value="false"
                                      onChange={() =>
                                        props.setFieldValue(
                                          `stories.${indexS}.storyFrames.${index}.buttonVisible`,
                                          false,
                                        )
                                      }
                                      checked={!storyFrame.buttonVisible}
                                    />
                                    Ссылка
                                  </label>
                                </div>
                                {storyFrame.buttonVisible ? (
                                  <>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.buttonText`}>
                                        Текст кнопки
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.buttonText`}
                                        placeholder=""
                                        type="text"
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.buttonText`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.buttonTextColor`}>
                                        Цвет текста
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.buttonTextColor`}
                                        component={ColorPicker}
                                        fieldName={`stories.${indexS}.storyFrames.${index}.buttonTextColor`}
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.buttonTextColor`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.buttonBackgroundColor`}>
                                        Цвет кнопки
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.buttonBackgroundColor`}
                                        component={ColorPicker}
                                        fieldName={`stories.${indexS}.storyFrames.${index}.buttonBackgroundColor`}
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.buttonBackgroundColor`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.buttonUrl`}>
                                        Ссылка на источник
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.buttonUrl`}
                                        placeholder=""
                                        type="text"
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.buttonUrl`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                  </>
                                ) : (
                                  <>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.linkText`}>
                                        Текст гиперссылки
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.linkText`}
                                        placeholder=""
                                        type="text"
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.linkText`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                    <div className="col">
                                      <label
                                        htmlFor={`stories.${indexS}.storyFrames.${index}.linkUrl`}>
                                        Ссылка на источник
                                      </label>
                                      <Field
                                        name={`stories.${indexS}.storyFrames.${index}.linkUrl`}
                                        placeholder=""
                                        type="text"
                                      />
                                      <ErrorMessage
                                        name={`stories.${indexS}.storyFrames.${index}.linkUrl`}
                                        component="div"
                                        className="field-error"
                                      />
                                    </div>
                                  </>
                                )}

                                <div className="col">
                                  <label
                                    htmlFor={`stories.${indexS}.storyFrames.${index}.gradient`}>
                                    Градиент
                                  </label>
                                  <Field
                                    as="select"
                                    name={`stories.${indexS}.storyFrames.${index}.gradient`}>
                                    <option value="EMPTY">Нет</option>
                                    <option value="FULL">Поверх всего изображения</option>
                                    <option value="HALF">Поверх нижней половины изображения</option>
                                  </Field>
                                  <ErrorMessage
                                    name={`stories.${indexS}.storyFrames.${index}.gradient`}
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
                            ))}
                            <button
                              type="button"
                              className="secondary"
                              onClick={() => {
                                if (props.values.stories[indexS].storyFrames.length <= 5) {
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

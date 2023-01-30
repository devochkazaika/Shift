import React from 'react';

import FormField from '../../FormField';
import ColorPicker from '../../ColorPicker';

import frameFieldsStyles from './FrameFields.module.scss';
import storyFormPartsStyles from '../StoryFormParts.module.scss';

const FrameFields = ({
  frameJson,
  storyIndex,
  frameIndex,
  framesCount,
  setFieldValue,
  remove,
  errors,
  touched,
}) => {
  const [open, setOpen] = React.useState(true);
  return (
    <div className={frameFieldsStyles.frame}>
      <div
        style={
          frameIndex === framesCount - 1 && !open
            ? { borderBottom: '2px solid #eeeff5' }
            : { borderBottom: 'none' }
        }
        className={frameFieldsStyles.frame_header}
        onClick={() => setOpen(!open)}>
        <div className={frameFieldsStyles.frame_title}>
          <span>
            {open ? (
              <svg height="15px" id="Layer_1" version="1.1" viewBox="0 0 512 512" width="15px">
                <rect height="64" width="384" x="64" y="224" />
              </svg>
            ) : (
              <svg
                fill="#000000"
                version="1.1"
                id="Capa_1"
                width="15px"
                height="15px"
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
            )}
          </span>
          <h3>Карточка {frameIndex + 1}</h3>
        </div>

        {frameIndex !== 0 && (
          <span onClick={() => remove(frameIndex)} className={frameFieldsStyles.bin_icon}>
            <span></span>
            <i></i>
          </span>
        )}
      </div>
      {open && (
        <div className={frameFieldsStyles.frame_content}>
          <div className={storyFormPartsStyles.input_field}>
            <FormField
              labelTitle={'Заголовок'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.title`}
              type={'text'}
              errors={errors}
              touched={touched}
            />
          </div>
          <div className={storyFormPartsStyles.row}>
            <div className={storyFormPartsStyles.input_field}>
              <FormField
                labelTitle={'Текст'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.text`}
                as={'textarea'}
                errors={errors}
                touched={touched}
              />
            </div>
            <FormField
              labelTitle={'Цвет текста'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.textColor`}
              component={ColorPicker}
              errors={errors}
              touched={touched}
            />
          </div>
          <div className={storyFormPartsStyles.input_field}>
            <FormField
              labelTitle={'Путь до картинки'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.pictureUrl`}
              type="text"
              errors={errors}
              touched={touched}
            />
          </div>

          <div role="group" aria-labelledby="my-radio-group">
            <div className={storyFormPartsStyles.row}>
              <label>
                <FormField
                  labelTitle="Кнопка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`}
                  value="true"
                  handleClick={() => {
                    setFieldValue(
                      `stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`,
                      true,
                    );
                  }}
                  checked={frameJson.buttonVisible}
                  errors={errors}
                  touched={touched}
                />
              </label>

              <label>
                <FormField
                  labelTitle="Ссылка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`}
                  value="false"
                  handleClick={() =>
                    setFieldValue(
                      `stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`,
                      false,
                    )
                  }
                  checked={!frameJson.buttonVisible}
                  errors={errors}
                  touched={touched}
                />
              </label>
            </div>
          </div>

          {frameJson.buttonVisible ? (
            <>
              <div className={storyFormPartsStyles.row}>
                <div className={storyFormPartsStyles.input_field}>
                  <FormField
                    labelTitle={'Текст кнопки'}
                    name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonText`}
                    as={'textarea'}
                    errors={errors}
                    touched={touched}
                  />
                </div>
                <FormField
                  labelTitle={'Цвет текста'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonTextColor`}
                  component={ColorPicker}
                  errors={errors}
                  touched={touched}
                />
              </div>
              <FormField
                labelTitle={'Цвет кнопки'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonBackgroundColor`}
                component={ColorPicker}
                errors={errors}
                touched={touched}
              />
              <div className={storyFormPartsStyles.input_field}>
                <FormField
                  labelTitle={'Ссылка на источник'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonUrl`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
            </>
          ) : (
            <>
              <div className={storyFormPartsStyles.input_field}>
                <FormField
                  labelTitle={'Текст гиперссылки'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkText`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
              <div className={storyFormPartsStyles.input_field}>
                <FormField
                  labelTitle={'Ссылка на источник'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkUrl`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
            </>
          )}

          <div className={storyFormPartsStyles.input_field}>
            <FormField
              labelTitle={'Градиент'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.gradient`}
              as="select"
              options={[
                { value: 'EMPTY', name: 'Нет' },
                { value: 'FULL', name: 'Поверх всего изображения' },
                { value: 'HALF', name: 'Поверх нижней половины изображения' },
              ]}
              errors={errors}
              touched={touched}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default FrameFields;

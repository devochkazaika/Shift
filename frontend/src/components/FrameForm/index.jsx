import React from 'react';
import { Field } from 'formik';

import FormField from '../FormField';
import ColorPicker from '../ColorPicker';

const FrameForm = ({ frameJson, storyIndex, frameIndex, setFieldValue, remove }) => {
  return (
    <>
      <div className="row">
        <FormField
          labelTitle={'Заголовок'}
          name={`stories.${storyIndex}.storyFrames.${frameIndex}.title`}
          type={'text'}
        />
        <FormField
          labelTitle={'Текст'}
          name={`stories.${storyIndex}.storyFrames.${frameIndex}.text`}
          type={'text'}
        />
        <FormField
          labelTitle={'Цвет текста'}
          name={`stories.${storyIndex}.storyFrames.${frameIndex}.textColor`}
          component={ColorPicker}
        />
        <FormField
          labelTitle={'Путь до картинки'}
          name={`stories.${storyIndex}.storyFrames.${frameIndex}.pictureUrl`}
          type="text"
        />

        <div role="group" aria-labelledby="my-radio-group">
          <label>
            <Field
              type="radio"
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`}
              value="true"
              onChange={(e) => {
                setFieldValue(
                  `stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`,
                  true,
                );
              }}
              checked={frameJson.buttonVisible}
            />
            Кнопка
          </label>
          <label>
            <Field
              type="radio"
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`}
              value="false"
              onChange={() =>
                setFieldValue(
                  `stories.${storyIndex}.storyFrames.${frameIndex}.buttonVisible`,
                  false,
                )
              }
              checked={!frameJson.buttonVisible}
            />
            Ссылка
          </label>
        </div>

        {frameJson.buttonVisible ? (
          <>
            <FormField
              labelTitle={'Текст кнопки'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonText`}
              type="text"
            />
            <FormField
              labelTitle={'Цвет текста'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonTextColor`}
              component={ColorPicker}
            />
            <FormField
              labelTitle={'Цвет кнопки'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonBackgroundColor`}
              component={ColorPicker}
            />
            <FormField
              labelTitle={'Ссылка на источник'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonUrl`}
              type="text"
            />
          </>
        ) : (
          <>
            <FormField
              labelTitle={'Текст гиперссылки'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkText`}
              type="text"
            />
            <FormField
              labelTitle={'Ссылка на источник'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkUrl`}
              type="text"
            />
          </>
        )}

        <FormField
          labelTitle={'Градиент'}
          name={`stories.${storyIndex}.storyFrames.${frameIndex}.gradient`}
          as="select"
          options={[
            { value: 'EMPTY', name: 'Нет' },
            { value: 'FULL', name: 'Поверх всего изображения' },
            { value: 'HALF', name: 'Поверх нижней половины изображения' },
          ]}
        />

        <div>
          <button type="button" className="secondary" onClick={() => remove(frameIndex)}>
            X
          </button>
        </div>
      </div>
    </>
  );
};

export default FrameForm;

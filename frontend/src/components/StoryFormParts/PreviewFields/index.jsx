import React from 'react';

import FormField from '../../FormField';
import ColorPicker from '../../ColorPicker';

import storyFormPartsStyles from '../StoryFormParts.module.scss';

const PreviewFields = ({ storyIndex, errors, touched }) => {
  return (
    <>
      <div className={storyFormPartsStyles.row}>
        <div className={storyFormPartsStyles.input_field}>
          <FormField
            labelTitle={'Заголовок истории'}
            name={`stories.${storyIndex}.previewTitle`}
            type={'text'}
            errors={errors}
            touched={touched}
          />
        </div>
        <FormField
          labelTitle={'Цвет текста'}
          name={`stories.${storyIndex}.previewTitleColor`}
          component={ColorPicker}
          errors={errors}
          touched={touched}
        />
      </div>
      <div className={storyFormPartsStyles.input_field}>
        <FormField
          labelTitle={'Путь до картинки'}
          name={`stories.${storyIndex}.previewUrl`}
          type="text"
          errors={errors}
          touched={touched}
        />
      </div>
      <div className={storyFormPartsStyles.input_field}>
        <FormField
          labelTitle={'Градиент истории'}
          name={`stories.${storyIndex}.previewGradient`}
          as="select"
          options={[
            { value: 'EMPTY', name: 'Нет' },
            { value: 'FULL', name: 'Поверх всего изображения' },
          ]}
          errors={errors}
          touched={touched}
        />
      </div>
    </>
  );
};

export default PreviewFields;

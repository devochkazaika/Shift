import React from 'react';

import FormField from '../../FormField';
import ColorPicker from '../../ColorPicker';
import UploadImage from '../../UploadImage';

import storyFormPartsStyles from '../StoryFormParts.module.scss';

const PreviewFields = ({ storyIndex, setFieldValue, errors, touched }) => {
  return (
    <>
      <div className={storyFormPartsStyles.row}>
        <div className={storyFormPartsStyles.input_field}>
          <FormField
            labelTitle={'Заголовок'}
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
          labelTitle={'Картинка'}
          name={`stories.${storyIndex}.previewUrl`}
          component={UploadImage}
          errors={errors}
          touched={touched}
        />
      </div>
      <div className={storyFormPartsStyles.input_field}>
        <FormField
          labelTitle={'Градиент'}
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

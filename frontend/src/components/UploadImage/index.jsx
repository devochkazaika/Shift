import React from 'react';

import { ReactComponent as UploadIcon } from '../../assets/icons/upload.svg';
import { convertImageToByteArray } from '../../utils/helpers/byteArrayFunctions';

import styles from './UploadImage.module.scss';

import PreviewImage from './PreviewImage';

const UploadImage = ({ field, form, ...props }) => {
  const inputRef = React.useRef();

  const handleUploadImage = async (e) => {
    let img = e.target.files[0];

    if (img) {
      form.setFieldValue(field.name, await convertImageToByteArray(img));
    }
  };

  return (
    <>
      <input
        className={styles.file_input}
        name={field.name}
        id={'file' + field.name}
        type="file"
        accept="image/*"
        onChange={handleUploadImage}
        onClick={(e) => (e.target.value = null)}
        ref={inputRef}
      />
      <label htmlFor={'file' + field.name}>
        <UploadIcon width="20" height="17" fill="white" /> <span>Выберите файл</span>
      </label>
      {field.value && (
        <PreviewImage
          image={field.value}
          setFieldValue={form.setFieldValue}
          fieldName={field.name}
          inputRef={inputRef}
        />
      )}
    </>
  );
};

export default UploadImage;

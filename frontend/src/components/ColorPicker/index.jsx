import React, { useState } from 'react';

import { SketchPicker } from 'react-color';
import rgbHex from 'rgb-hex';

import styles from './ColorPicker.module.scss';

const ColorPicker = ({ field, form, ...props }) => {
  const [modalShown, toggleModal] = React.useState(false);
  const colorAreaRef = React.useRef();

  React.useEffect(() => {
    const closeModal = (e) => {
      if (!colorAreaRef.current.contains(e.target)) {
        toggleModal(false);
      }
    };

    document.body.addEventListener('click', closeModal);
    return () => document.body.removeEventListener('click', closeModal);
  }, [field.value]);

  const handleColorChange = (c) => {
    let hexColor = '#' + rgbHex(c.rgb.r, c.rgb.g, c.rgb.b, c.rgb.a);
    form.setFieldValue(field.name, hexColor);
  };

  return (
    <div className={styles.color_area} ref={colorAreaRef}>
      <div
        className={styles.color_window}
        style={{ background: field.value }}
        onClick={() => {
          toggleModal(!modalShown);
        }}></div>
      {modalShown && <SketchPicker color={field.value} onChange={handleColorChange} />}
    </div>
  );
};

export default ColorPicker;

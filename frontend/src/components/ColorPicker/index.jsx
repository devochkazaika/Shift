import React from 'react';
import { SketchPicker } from 'react-color';
import rgbHex from 'rgb-hex';

import useOutsideClick from '../../hooks/useOutsideClick';
import styles from './ColorPicker.module.scss';

const ColorPicker = ({ field, form, onChange }) => {
  const [modalShown, toggleModal] = React.useState(false);
  const colorAreaRef = React.useRef();

  const closeModal = () => {
    toggleModal(false);
  };

  useOutsideClick(colorAreaRef, closeModal);

  const handleColorChange = (c) => {
    let hexColor = '#' + rgbHex(c.rgb.r, c.rgb.g, c.rgb.b, c.rgb.a);
    form.setFieldValue(field.name, hexColor);
    if (onChange) {
      onChange({ target: { name: field.name, value: hexColor } });
    }
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

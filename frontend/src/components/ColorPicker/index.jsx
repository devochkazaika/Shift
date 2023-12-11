import React from 'react';
import { SketchPicker } from 'react-color';
import rgbHex from 'rgb-hex';

import useOutsideClick from '../../hooks/useOutsideClick';
import styles from './ColorPicker.module.scss';

// TODO: не понял зачем тут ...props
// eslint-disable-next-line no-unused-vars
const ColorPicker = ({ field, form, ...props }) => {
  const [modalShown, toggleModal] = React.useState(false);
  const colorAreaRef = React.useRef();

  const closeModal = () => {
    toggleModal(false);
  };

  useOutsideClick(colorAreaRef, closeModal);

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

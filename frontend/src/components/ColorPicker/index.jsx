import React, { useState } from 'react';

import { SketchPicker } from 'react-color';
import rgbHex from 'rgb-hex';

import styles from './ColorPicker.module.scss';

const ColorPicker = ({ field, form, ...props }) => {
  const [color, setColor] = useState(field.value);
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
  }, [color]);

  const handleColorChange = (c) => {
    let hexColor = '#' + rgbHex(c.rgb.r, c.rgb.g, c.rgb.b, c.rgb.a);
    setColor(hexColor);
    form.setFieldValue(field.name, hexColor);
  };

  return (
    <div className={styles.color_area} ref={colorAreaRef}>
      <div
        className={styles.color_window}
        style={{ background: color }}
        onClick={() => {
          toggleModal(!modalShown);
        }}></div>
      {modalShown && <SketchPicker color={color} onChange={handleColorChange} />}
    </div>
  );
};

export default ColorPicker;

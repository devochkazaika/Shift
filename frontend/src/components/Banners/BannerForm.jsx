// import { FieldArray } from 'formik';
import React from 'react';

// import { initialStoryFrame, maxFrames } from '../../utils/constants/initialValues';

// import Button from '.././ui/Button';
// import FrameFields from './BannerFormParts/FrameFields';
// import PreviewFields from './BannerFormParts/PreviewFields';

// import { ReactComponent as ArrowIcon } from '../../assets/icons/arrow-up.svg';

const BannerForm = ({ banner, bannerIndex}) => {
  return (
    <div>
      <h2>Превью</h2>
      {/* <PreviewFields storyIndex={props} {...props} /> */}
      <br />

      <h2>Карточки</h2>
      {/* <FieldArray name={`banners.${bannerIndex}`}> */}
        {() => (
          <>
            <div>{banner}</div>
            <div>{bannerIndex}</div>
          </>
        )}
      {/* </FieldArray> */}
    </div>
  );
};

export default BannerForm;

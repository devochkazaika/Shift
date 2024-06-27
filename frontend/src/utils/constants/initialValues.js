import { banks } from './banks';

export const maxFrames = 6;

export const initialStoryFrame = {
  title: '',
  text: '',
  textColor: '#000',
  gradient: 'EMPTY',
  visibleLinkOrButtonOrNone: 'BUTTON',
  pictureUrl: null,
  linkText: '',
  linkUrl: '',
  buttonText: '',
  buttonTextColor: '#000',
  buttonBackgroundColor: '#fff',
  buttonUrl: '',
};

const initialStoryPreview = {
  previewTitle: '',
  previewTitleColor: '#000',
  previewUrl: null,
  previewGradient: 'EMPTY',
};

export const initialStoryValues = {
  bankId: banks[0].id,
  platformType: 'ALL PLATFORMS',
  stories: [
    {
      ...initialStoryPreview,
      storyFrames: [initialStoryFrame],
    },
  ],
};

export const initialBannerFrame = {
  title: '',
  text: '',
  textColor: '#000',
  gradient: 'EMPTY',
  visibleLinkOrButtonOrNone: 'BUTTON',
  pictureUrl: null,
  linkText: '',
  linkUrl: '',
  buttonText: '',
  buttonTextColor: '#000',
  buttonBackgroundColor: '#fff',
  buttonUrl: '',
};


import {
  defaultToastOptions,
  defaultUpdateToastOptions,
} from "../utils/constants/toastOptions";
import { toast } from "react-toastify";

import { defaultToastMessages } from "../utils/constants/defaultToastMessages";
import api from "./api";


const createToast = (toastContent) => {
  return toast(toastContent, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
};

const updateToast = (toastView, response) => {
  console.log(response);
  toast.update(toastView, {
    ...defaultUpdateToastOptions,
    render: response.message,
  });
};

const warningToast = (toastView, error) => {
  if (error.response.status === 500) {
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: defaultToastMessages.connectionError,
      type: "warning",
    });
    return false;
  }
};

// const serverError = (toastView) => {
//   toast.update(toastView, {
//     ...defaultUpdateToastOptions,
//     render: "500 - Ошибка на стороне сервера",
//     type: "error",
//   });
// }

const errorFrameSize = (toastView) => {
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: "Недопустимое количество карточек",
      type: "warning",
    });
    return false;
};


export const getStories = async (bankId, platform) => {
  try {
    const response = await api.get('/stories/get/all', {
      params: { bankId, platform },
      responseType: 'json',
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

export const getUnApprovedStoriesByBank = async (bankId, platform) => {
  try {
    const response = await api.get(`/stories/admin/getUnApprovedStories?bankId=${bankId}&platform=${platform}`, {
      responseType: 'json'
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

export const getDeletedStories = async () => {
  try {
    const response = await api.get('/stories/admin/bank/info/getDeletedStories/', {
      responseType: 'json'
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

export const getDeletedStoriesByBank = async (bankId, platform) => {
  try {
    const response = await api.get(`/stories/admin/getDeletedStories?bankId=${bankId}&platform=${platform}`, {

      responseType: 'json'
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

export const uploadStories = async (jsonPayload, previewImage, cardImages) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  const form = new FormData();
  form.append("json", JSON.stringify(jsonPayload));
  form.append("previewImage", previewImage);
  cardImages.map((image) => {
    form.append("cardImages", image);
  });
  try {
    const response = await api.post("/stories/add/story", form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const fetchImage = async (url, setImage) => {
  try {
    const response = await api.get(`image?path=${url}`, { responseType: 'arraybuffer' });
    const blob = new Blob([response.data], { type: response.headers['content-type'] });
    const file = new File([blob], "initial_image.jpg", { type: blob.type });
    setImage(file);
  } catch (error) {
    console.error("Error fetching the image:", error);
  }
};

export const deleteStory = async (story, platform) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  if (
    !story ||
    !Object.hasOwn(story, "bankId") ||
    !Object.hasOwn(story, "id")
  ) {
    return false;
  }
  try {
    const response = await api.delete(`/stories/delete/story?bankId=${story.bankId}&platform=${platform}&id=${story.id}`);
    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const deleteStoryFromDb = async (story, platform) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  if (!story || !Object.hasOwn(story, 'bankId') || !Object.hasOwn(story, 'id')) {
    return false;
  }
  try {
    const response = await api.delete(`/stories/admin/bank/info/deletefromDb?bankId=${story.bankId}&platform=${platform}&id=${story.id}`);
    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const deleteFrame = async (story, frame, platform) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  try {
    const response = await api.delete(`/stories/delete/frame?bankId=${story.bankId}&platform=${platform}&storyId=${story.id}&frameId=${frame.id}`);

    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const updateStory = async (story, storyIndex, jsonStory, platform) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  const form = new FormData();
  form.append("json", JSON.stringify(jsonStory));
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("image", jsonStory[`previewUrl_${storyIndex}`]);
  try {
    const response = await api.patch(`/stories/change/story`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const updateFrame = async (
  story,
  platform,
  frame,
  storyIndex,
  frameId,
  frameIndex,
) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  const form = new FormData();
  form.append("json", JSON.stringify(frame));
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("frameId", frameId);
  form.append("image", frame[`pictureFrame_${storyIndex}_${frameIndex}`]);
  try {
    const response = await api.patch(`/stories/change/frame`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    updateToast(toastView, response);
    return true;
  } catch (error) {
    warningToast(toastView, error);
  }
};

export const addFrame = async (story, storyIndex, frame, platform) => {
  const toastView = createToast(defaultToastMessages.uploadingData);
  const form = new FormData();
  form.append(
    "json",
    JSON.stringify({
      title: frame.title,
      text: frame.text,
      visibleButtonOrNone: frame.visibleButtonOrNone,
      gradient: frame.gradient,
      textColor: frame.textColor,
      buttonText: frame.buttonText,
      buttonTextColor: frame.buttonTextColor,
      buttonBackgroundColor: frame.buttonBackgroundColor,
      buttonUrl: frame.buttonUrl,
    }),
  );
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("image", frame[`pictureUrl_${storyIndex}_add`]);
  try {
    const response = await api.post(`/stories/add/frame`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    console.log(response.data);
    updateToast(toastView, response);
    return response;
  } catch (exception) {
    errorFrameSize(toastView);
  }
};

export const updateFrameOrder = async (story, platform, newOrder) => {
  const toastView = createToast(defaultToastMessages.uploadingData);

  const form = new FormData();
  form.append("bankId", story.bankId);
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("newOrder", newOrder);
  try {
    const response = await api.patch(`/stories/change/frame/swap`, form);
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return response;
  } catch (error) {
    warningToast(toastView, error);
  }
};


export const getAllHistory= async () => {
  try {
    const response = await api.get('/history/stories/getAllHistory', {
      responseType: 'json',
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};
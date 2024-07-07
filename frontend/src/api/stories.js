import axios from "axios";
import { defaultToastOptions, defaultUpdateToastOptions } from '../utils/constants/toastOptions';
import { toast } from "react-toastify";
import { defaultToastMessages } from "../utils/constants/defaultToastMessages";

export const uploadStories = async (jsonPayload, previewImage, cardImages) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  const form = new FormData();
  form.append("json", JSON.stringify(jsonPayload));
  form.append("previewImage", previewImage);
  cardImages.map((image) => {
      form.append("cardImages", image);
  });
  try {
    const response = await axios.post("/stories/add", form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return true;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: error.message,
      type: "warning"
    });
    return false;
  }
};

export const deleteStory = async (value, platform) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  try {
    const response = await axios.delete(`/stories/bank/info/delete?bankId=${value.bankId}&platform=${platform}&id=${value.id}`);
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return true;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
  }
};

export const deleteFrame = async (story, frame, platform) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  try {
    const response = await axios.delete(`/stories/bank/info/delete/frame?bankId=${story.bankId}&platform=${platform}&storyId=${story.id}&frameId=${frame.id}`);
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return true;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
  }
};

export const updateStory = async (story, jsonStory, platform) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  const form = new FormData();
  form.append("json", JSON.stringify(jsonStory));
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("image", jsonStory.previewUrl);
  try {
    const response = await axios.patch(`/stories/bank/info/change`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return true;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
  }
};

export const updateFrame = async (story, platform, frame, frameIndex) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  const form = new FormData();
  form.append("json", JSON.stringify(frame));
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("frameId", frameIndex)
  form.append("image", frame.pictureFrame)
  try {
    const response = await axios.patch(`/stories/bank/info/change/frame`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return true;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
  }
};


export const addFrame = async (story, values, platform) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  const form = new FormData();
  form.append("json", JSON.stringify({
    title: values.title,
    text: values.text,
    visibleButtonOrNone: values.visibleButtonOrNone,
    gradient: values.gradient,
    textColor: values.textColor,
    buttonText: values.buttonText,
    buttonTextColor: values.buttonTextColor,
    buttonBackgroundColor: values.buttonBackgroundColor,
    buttonUrl: values.buttonUrl
  }
  ));
  form.append("platform", platform);
  form.append("id", story.id);
  form.append("bankId", story.bankId);
  form.append("image", values.pictureUrl);
  try {
    const response = await axios.post(`/stories/add/frame`, form);
    toast.update(toastView, {
      ...defaultUpdateToastOptions,
      render: response.message,
    });
    return response;
  } catch (error) {
    if (error.response.status === 500) {
      toast.update(toastView, {
        ...defaultUpdateToastOptions,
        render: defaultToastMessages.connectionError,
        type: "warning"
      });
      return false;
    }
  }
};
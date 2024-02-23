import axios from "axios";
import { defaultToastOptions, defaultUpdateToastOptions } from '../utils/constants/toastOptions';
import { toast } from "react-toastify";
import { defaultToastMessages } from "../utils/constants/defaultToastMessages";

export const uploadStories = async (jsonPayload, images) => {
  const toastView = toast(defaultToastMessages.uploadingData, {
    ...defaultToastOptions,
    autoClose: false,
    isLoading: true,
  });
  const form = new FormData();
  form.append("json", jsonPayload);
  form.append("images", images);
  try {
    const response = await axios.post("/stories/add", jsonPayload, {
      headers: { "Content-Type": "application/json" },
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

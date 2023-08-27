import { appConfig } from "app/app-config";
import { getApiPath } from "shared/utils/formatters/api-path";

const { apiPrefix } = appConfig;

export const baseUrl = getApiPath(apiPrefix);

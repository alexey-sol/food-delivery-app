export const getApiPath = (prefix: string, path?: string): string => {
    const basePath = `/${prefix}`;

    return path
        ? `${basePath}/${path}`
        : `${basePath}`;
};

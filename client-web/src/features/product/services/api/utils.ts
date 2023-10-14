import * as cn from "./const";

export const createTag = (id: string | number = cn.TAG_LIST_ID): {
    id: string | number;
    type: typeof cn.PRODUCT_TAG_TYPE;
} => ({
    id,
    type: cn.PRODUCT_TAG_TYPE,
});

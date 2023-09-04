export type SaveCartItemArg = {
    quantity: number; // TODO positive | neg
    productId: number;
    userId: number;

    placeId: number; // TODO for now, it's getting past only for updating frontend cache in api
};

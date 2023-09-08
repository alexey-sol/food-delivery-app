export type SaveCartItemArg = {
    count: number;
    operation: "ADD" | "REMOVE";
    placeId: number;
    productId: number;
    userId: number;
};

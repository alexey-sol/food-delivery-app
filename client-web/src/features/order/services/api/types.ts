type CreateOrderItemDto = {
    quantity: number;
    // productId: number;
    products: number[];
};

export type CreateOrderArg = {
    orderItems: CreateOrderItemDto[];
    storeId: number;
    userId: number;
};

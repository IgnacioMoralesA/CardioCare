export interface DashboardItemRequest {
  widgetName: string;
  dataJson: string; // JSON en formato string
  ownerId: number;
}

export interface DashboardItemResponse {
  id: number;
  widgetName: string;
  dataJson: string;
  ownerId: number;
}

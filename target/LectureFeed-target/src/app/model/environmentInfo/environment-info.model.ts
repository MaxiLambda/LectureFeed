export class EnvironmentInfo {
  private readonly _routingIpInterface: string|null;

  constructor(routingIpInterface: string|null) {
    this._routingIpInterface = routingIpInterface;
  }

  get routingIpInterface(): string|null {
    return this._routingIpInterface;
  }
}

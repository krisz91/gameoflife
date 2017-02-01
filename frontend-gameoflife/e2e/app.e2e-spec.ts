import { FrontendGameoflifePage } from './app.po';

describe('frontend-gameoflife App', function() {
  let page: FrontendGameoflifePage;

  beforeEach(() => {
    page = new FrontendGameoflifePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
